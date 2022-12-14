package ru.msokolov.notesapp.screens.profile.change

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.msokolov.notesapp.R

class ChangeProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeProfileFragment()
    }

    private lateinit var viewModel: ChangeProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}