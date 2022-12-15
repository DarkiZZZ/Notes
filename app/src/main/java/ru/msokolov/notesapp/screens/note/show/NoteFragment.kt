package ru.msokolov.notesapp.screens.note.show

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {

    companion object {
        fun newInstance() = NoteFragment()
    }

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        // TODO: Use the ViewModel


    }

}