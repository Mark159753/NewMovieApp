package com.example.movieapp.ui.comingSoon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R

class ComingSoonFragment : Fragment() {

    companion object {
        fun newInstance() = ComingSoonFragment()
    }

    private lateinit var viewModel: ComingSoonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coming_soon_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComingSoonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}