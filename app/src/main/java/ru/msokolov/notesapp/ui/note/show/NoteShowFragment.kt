@file:Suppress("DEPRECATION")

package ru.msokolov.notesapp.ui.note.show

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentNoteShowBinding

@AndroidEntryPoint
class NoteShowFragment : Fragment() {

    private val showViewModel: NoteShowViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    private lateinit var binding: FragmentNoteShowBinding

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNoteShowBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = showViewModel


        adapter = NoteAdapter(NoteClickListener { noteEntity ->
            findNavController().navigate(NoteShowFragmentDirections
                .actionNoteShowFragmentToUpdateNoteFragment(noteEntity))
        })

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                showViewModel.getAllNotes.collect{ notes ->
                    adapter.submitList(notes)
                }
            }
        }

        binding.apply {
            recyclerView.adapter = adapter
            floatingActionButton.setOnClickListener {
                val title: String = ""
                val priority:Int = 0
                val timestamp:Long = System.currentTimeMillis()
            val note = NoteEntity(
                id = 0,
                title = title,
                priority = priority,
                timestamp = timestamp)
                showViewModel.editNote(note)

                CoroutineScope(Main).launch {
                    var currentNote = showViewModel.getLastFetchedNote()
                    if (currentNote == null){
                        currentNote = note
                        findNavController().navigate(NoteShowFragmentDirections
                            .actionNoteShowFragmentToAddNoteFragment(currentNote))
                    }
                    else{
                        findNavController().navigate(NoteShowFragmentDirections
                            .actionNoteShowFragmentToAddNoteFragment(currentNote))
                    }

                }


            }
        }

        ItemTouchHelper(object  : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val noteEntity = adapter.currentList[position]
                showViewModel.deleteNote(noteEntity)
                showViewModel.deleteAllByNoteId(noteEntity.id)

                Snackbar.make(binding.root, "Deleted!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        showViewModel.editNote(noteEntity)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.recyclerView)


        setHasOptionsMenu(true)

        hideKeyboard(requireActivity())

        return binding.root
    }


    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    runQuery(newText)
                }
                return true
            }
        })
    }

    fun runQuery(query: String){
        val searchQuery = "%$query%"
        showViewModel.searchDatabaseForNotes(searchQuery).observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_priority -> {
                lifecycleScope.launch{
                    repeatOnLifecycle(Lifecycle.State.STARTED){
                        showViewModel.getAllPriorityNotes.collectLatest { tasks ->
                            adapter.submitList(tasks)
                        }
                    }
                }
            }
            R.id.action_delete_all -> deleteAllItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItem() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes"){dialog, _ ->
                showViewModel.deleteAllData()
                dialog.dismiss()
            }.setNegativeButton("No"){dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }


}