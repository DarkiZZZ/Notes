package ru.msokolov.notesapp.ui.note.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.data.room.NoteEntity
import ru.msokolov.notesapp.databinding.FragmentUpdateNoteBinding
import ru.msokolov.notesapp.ui.note.show.NoteViewModel

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()

    private lateinit var binding: FragmentUpdateNoteBinding

    private val args by navArgs<UpdateNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)

        binding.apply {
            updateEdtTask.setText(args.note.title)
            updateSpinner.setSelection(args.note.priority)

            btnUpdate.setOnClickListener {
                if(TextUtils.isEmpty(updateEdtTask.text)){
                    Toast.makeText(requireContext(), "It's empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val taskTitle = updateEdtTask.text.toString()
                val priority = updateSpinner.selectedItemPosition

                val taskEntry = NoteEntity(
                    args.note.id,
                    taskTitle,
                    priority,
                    args.note.timestamp
                )

                viewModel.update(taskEntry)
                Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateNoteFragment_to_noteShowFragment)
            }
        }
        return binding.root
    }

}