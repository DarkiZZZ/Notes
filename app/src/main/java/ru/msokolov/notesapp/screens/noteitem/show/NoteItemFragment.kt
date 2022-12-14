package ru.msokolov.notesapp.screens.noteitem.show

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.msokolov.notesapp.R

class NoteItemFragment : Fragment() {

    companion object {
        fun newInstance() = NoteItemFragment()
    }

    private lateinit var viewModel: NoteItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}