package com.example.movieapp.ui.comingSoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.ui.comingSoon.adapter.ItemClickListener
import com.example.movieapp.ui.comingSoon.adapter.MovieAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class ComingMoviesFragment : Fragment(), ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ComingSoonViewModel by activityViewModels{ viewModelFactory }

    private lateinit var list:RecyclerView
    private val movieAdapter = MovieAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coming_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectMe()
        list = view.findViewById(R.id.coming_movies_list)

        initAdapter()
        initMovieList()
    }

    private fun initMovieList() {
        list.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun initAdapter(){
        movieAdapter.setClickListener(this)
        lifecycleScope.launch {
            val genres = viewModel.getMovieGenre()
            movieAdapter.setMovieGenreList(genres)
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    override fun onItemClicked(id: Int) {
        val action = ComingSoonFragmentDirections.actionComingSoonFragmentToMovieDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
            .create()
            .inject(this)
    }

}