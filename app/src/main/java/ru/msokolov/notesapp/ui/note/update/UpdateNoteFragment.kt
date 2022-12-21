package ru.msokolov.notesapp.ui.note.update

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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentUpdateNoteBinding
import ru.msokolov.notesapp.ui.item.CustomInputDialogFragment
import ru.msokolov.notesapp.ui.item.CustomInputDialogListener

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {

    private val updateViewModel: UpdateNoteViewModel by viewModels()

    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var adapter: ItemAdapter

    private val args by navArgs<UpdateNoteFragmentArgs>()

    private lateinit var currentItemClicked: ItemEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = updateViewModel
        updateViewModel.currentNoteId = args.currentNote.id

        adapter = ItemAdapter(ItemClickListener { itemEntity ->
            showCustomInputDialogFragment(KEY_UPDATE_REQUEST_KEY, itemEntity.text)
        })

        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                updateViewModel.getAllItems.collect{ items ->
                    adapter.submitList(items)
                }
            }
        }

        setupCustomInputDialogFragmentListeners()

        //update



        binding.recyclerView.adapter = adapter

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
                val itemEntity = adapter.currentList[position]
                updateViewModel.deleteItem(itemEntity)

                Snackbar.make(binding.root, "Deleted!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        updateViewModel.insertItem(itemEntity)
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

                updateViewModel.editNote(noteEntity)

                Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(UpdateNoteFragmentDirections
                    .actionUpdateNoteFragmentToNoteShowFragment(noteEntity))
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

    private fun setupCustomInputDialogFragmentListeners() {
        val listener: CustomInputDialogListener = { requestKey, text ->
            when (requestKey) {
                KEY_UPDATE_REQUEST_KEY-> {
                    currentItemClicked.text = text
                    updateViewModel.insertItem(itemEntity = currentItemClicked)
                }
            }
        }
        CustomInputDialogFragment.setupListener(parentFragmentManager, this, KEY_UPDATE_REQUEST_KEY, listener)
    }




    companion object {
        @JvmStatic private val KEY_UPDATE_REQUEST_KEY = "KEY_TEXT_UPDATE_REQUEST_KEY"
        @JvmStatic private val KEY_TEXT = "KEY_TEXT"
    }
}