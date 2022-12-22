package ru.msokolov.notesapp.ui.note.edit.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentAddNoteBinding
import ru.msokolov.notesapp.ui.note.edit.*

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private val editViewModel: EditNoteViewModel by viewModels()

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var itemAdapter: ItemAdapter

    private lateinit var currentItemClicked: ItemEntity

    private val args by navArgs<AddNoteFragmentArgs>()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        itemAdapter = ItemAdapter(ItemClickListener { itemEntity ->
            currentItemClicked = itemEntity
            showCustomInputDialogFragment(KEY_ADD_REQUEST_KEY, itemEntity.text)
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

        //Создаем dialogFragment с пустым editView(add)
        binding.btnAddItem.setOnClickListener {
            currentItemClicked = ItemEntity(
                0,
                args.currentNote.id,
                ""
            )
            showCustomInputDialogFragment(KEY_ADD_REQUEST_KEY, "")
        }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.priorities))

        binding.apply {
            spinner.adapter = spinnerAdapter
            btnAdd.setOnClickListener {
                if(TextUtils.isEmpty((edtTask.text))){
                    Toast.makeText(requireContext(), "It's empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                args.currentNote.title = edtTask.text.toString()
                args.currentNote.priority = spinner.selectedItemPosition

                editViewModel.editNote(args.currentNote)

                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(AddNoteFragmentDirections
                    .actionAddNoteFragmentToNoteShowFragment())
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
                KEY_ADD_REQUEST_KEY -> {
                    currentItemClicked.text = text
                    editViewModel.editItem(itemEntity = currentItemClicked)
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }
        CustomInputDialogFragment.setupListener(
            parentFragmentManager,
            this,
            KEY_ADD_REQUEST_KEY,
            listener
        )
    }




    companion object {
        @JvmStatic private val KEY_ADD_REQUEST_KEY = "KEY_TEXT_ADD_REQUEST_KEY"
        @JvmStatic private val KEY_TEXT = "KEY_TEXT_ADD"
    }

    override fun onStop() {
        if(args.currentNote.title.isBlank()){
            editViewModel.deleteNote(args.currentNote)
        }
        Thread.sleep(200)
        super.onStop()
    }


}
