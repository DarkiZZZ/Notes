package ru.msokolov.notesapp.screens.profile.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.msokolov.notesapp.R

class AddProfileFragment : Fragment() {

    companion object {
        fun newInstance() = AddProfileFragment()
    }

    private lateinit var viewModel: AddProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}