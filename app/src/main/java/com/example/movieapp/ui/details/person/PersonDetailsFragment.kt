package com.example.movieapp.ui.details.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movieapp.databinding.PersonDetailsFragmentBinding

class PersonDetailsFragment : Fragment() {


    private val viewModel: PersonDetailsViewModel by viewModels()

    private var binding:PersonDetailsFragmentBinding? = null

    val args:PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = PersonDetailsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO Don't forget to implement this Fragment
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}