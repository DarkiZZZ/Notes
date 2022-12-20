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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.data.room.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentAddNoteBinding
import ru.msokolov.notesapp.ui.note.show.NoteViewModel

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()

    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.priorities)
        )

        binding.apply {
            spinner.adapter = adapter
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

                viewModel.insert(noteEntity)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addNoteFragment_to_noteShowFragment)
            }
        }
        return binding.root
    }

}