package ru.msokolov.notesapp.ui.note.add

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentAddNoteBinding
import ru.msokolov.notesapp.ui.item.CustomInputDialogFragment
import ru.msokolov.notesapp.ui.item.CustomInputDialogListener
import ru.msokolov.notesapp.ui.note.update.*

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private val addViewModel: UpdateNoteViewModel by viewModels()

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var adapter: ItemAdapter

    private lateinit var currentItemClicked: ItemEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = addViewModel

        adapter = ItemAdapter(ItemClickListener { itemEntity ->
            showCustomInputDialogFragment(KEY_UPDATE_REQUEST_KEY, itemEntity.text)
        })

        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                addViewModel.getAllItems.collect{ items ->
                    adapter.submitList(items)
                }
            }
        }

        setupCustomInputDialogFragmentListeners()

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.priorities)
        )

        binding.apply {
            spinner.adapter = spinnerAdapter
            btnAdd.setOnClickListener {
                if(TextUtils.isEmpty((edtTask.text))){
                    Toast.makeText(requireContext(), "It's empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val title = edtTask.text.toString()
                val priority = spinner.selectedItemPosition

                val noteEntity = NoteEntity(
                    0,
                    title,
                    priority,
                    System.currentTimeMillis()
                )
                addViewModel.editNote(noteEntity)

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

    private fun setupCustomInputDialogFragmentListeners() {
        val listener: CustomInputDialogListener = { requestKey, text ->
            when (requestKey) {
                KEY_UPDATE_REQUEST_KEY-> {
                    currentItemClicked.text = text
                    addViewModel.insertItem(itemEntity = currentItemClicked)
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