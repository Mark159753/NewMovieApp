package com.example.movieapp.ui.comingSoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.ui.comingSoon.adapter.TvAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class ComingTvShowFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ComingSoonViewModel by activityViewModels{ viewModelFactory }

    private lateinit var list:RecyclerView
    private val tvAdapter = TvAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coming_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectMe()
        list = view.findViewById(R.id.coming_tv_show_list)

        initTvAdapter()
        initTvList()
    }

    private fun initTvList() {
        list.apply {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun initTvAdapter() {
        lifecycleScope.launch {
            val genre = viewModel.getTvShowGenre()
            tvAdapter.setTvGenreList(genre)
            viewModel.tv.collectLatest {
                tvAdapter.submitData(it)
            }
        }
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
            .create()
            .inject(this)
    }

}