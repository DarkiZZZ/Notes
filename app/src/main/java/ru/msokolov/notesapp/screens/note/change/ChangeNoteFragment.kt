package ru.msokolov.notesapp.screens.note.change

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.msokolov.notesapp.R

class ChangeNoteFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeNoteFragment()
    }

    private lateinit var viewModel: ChangeNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}