package com.example.movieapp.ui.details.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movieapp.databinding.TvDetailsFragmentBinding

class TvDetailsFragment : Fragment() {

    private val viewModel: TvDetailsViewModel by viewModels()

    private var binding:TvDetailsFragmentBinding? = null

    val args:TvDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvDetailsFragmentBinding.inflate(inflater, container, false)
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