package ru.msokolov.notesapp.ui.note.edit.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentUpdateNoteBinding
import ru.msokolov.notesapp.ui.note.edit.*

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {

    private val editViewModel: EditNoteViewModel by viewModels()

    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var currentItemClicked: ItemEntity

    private val args by navArgs<UpdateNoteFragmentArgs>()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        itemAdapter = ItemAdapter(ItemClickListener { itemEntity ->
            currentItemClicked = itemEntity
            showCustomInputDialogFragment(KEY_UPDATE_REQUEST_KEY, itemEntity.text)
        })

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                editViewModel.getAllItemsByNoteId(args.currentNote.id).collect{ items ->
                    itemAdapter.submitList(items)
                }
            }
        }
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                editViewModel.getAllItemsByNoteId(args.currentNote.id).collect{ items ->
                    itemAdapter.submitList(items)
                }
            }
        }

        setupCustomInputDialogFragmentListeners()

        binding.recyclerView.adapter = itemAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

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
                val itemEntity = itemAdapter.currentList[position]
                editViewModel.deleteItem(itemEntity)

                Snackbar.make(binding.root, "Deleted!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        editViewModel.editItem(itemEntity)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.recyclerView)

        binding.btnAddItem.setOnClickListener {
            currentItemClicked = ItemEntity(
                0,
                args.currentNote.id,
                ""
            )
            showCustomInputDialogFragment(KEY_UPDATE_REQUEST_KEY, "")
        }

        binding.apply {
            updateEdtTask.setText(args.currentNote.title)
            updateSpinner.setSelection(args.currentNote.priority)

            btnUpdate.setOnClickListener {
                if(TextUtils.isEmpty(updateEdtTask.text)){
                    Toast.makeText(requireContext(), "It's empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val noteTitle = updateEdtTask.text.toString()
                val priority = updateSpinner.selectedItemPosition

                val noteEntity = NoteEntity(
                    args.currentNote.id,
                    noteTitle,
                    priority,
                    args.currentNote.timestamp
                )

                editViewModel.editNote(noteEntity)

                Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(UpdateNoteFragmentDirections
                    .actionUpdateNoteFragmentToNoteShowFragment())
            }
        }
        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TEXT, currentItemClicked.text)
    }

    private fun showCustomInputDialogFragment(requestKey: String, text: String) {
        CustomInputDialogFragment.show(parentFragmentManager, text, requestKey)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupCustomInputDialogFragmentListeners() {
        val listener: CustomInputDialogListener = { requestKey, text ->
            when (requestKey) {
                KEY_UPDATE_REQUEST_KEY -> {
                    currentItemClicked.text = text
                    editViewModel.editItem(itemEntity = currentItemClicked)
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }
        CustomInputDialogFragment.setupListener(
            parentFragmentManager,
            this,
            KEY_UPDATE_REQUEST_KEY,
            listener
        )
    }

    companion object {
        @JvmStatic private val KEY_UPDATE_REQUEST_KEY = "KEY_TEXT_UPDATE_REQUEST_KEY"
        @JvmStatic private val KEY_TEXT = "KEY_TEXT_UPDATE"
    }
}