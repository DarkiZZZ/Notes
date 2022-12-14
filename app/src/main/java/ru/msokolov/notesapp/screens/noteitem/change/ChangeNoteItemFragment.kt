package ru.msokolov.notesapp.screens.noteitem.change

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.msokolov.notesapp.R

class ChangeNoteItemFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeNoteItemFragment()
    }

    private lateinit var viewModel: ChangeNoteItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_note_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeNoteItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}