package com.example.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.databinding.HomeFragmentBinding
import com.example.movieapp.ui.home.adapter.HomeItem
import com.example.movieapp.ui.home.adapter.inner.HomeTrendAdapter
import com.example.movieapp.ui.home.adapter.inner.HomeMovieAdapter
import com.example.movieapp.ui.home.adapter.inner.HomeTvShowAdapter
import com.example.movieapp.ui.home.adapter.outer.OuterAdapter
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.example.movieapp.until.ConnectionLiveData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment(), ItemClickListener {

    @Inject
    lateinit var viewModelFactory:ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels{ viewModelFactory }
    private var binder:HomeFragmentBinding? = null

    private lateinit var outerAdapter: OuterAdapter
    private val trendAdapter = HomeTrendAdapter()
    private val streamingMovieAdapter = HomeMovieAdapter()
    private val popularTvShowAdapter = HomeTvShowAdapter()
    private val popularMovieAdapter = HomeMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = HomeFragmentBinding.inflate(inflater, container, false)
        return binder!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectMe()
        observeConnection()

        initTrendAdapter()
        initStreamAdapter()
        initPopularTvShowAdapter()
        initPopularMoviesAdapter()
        initOuterAdapter()

        initHomeList()
    }



    private fun initTrendAdapter(){
        trendAdapter.setClickListener(this)
        lifecycleScope.launch {
            viewModel.trend
                    .collectLatest {
                trendAdapter.submitData(it)
            }
        }
    }

    private fun initStreamAdapter(){
        streamingMovieAdapter.setListener(this)
        lifecycleScope.launch {
            viewModel.nowStreamingMovies
                    .collectLatest {
                        streamingMovieAdapter.submitData(it)
                    }
        }
    }

    private fun initPopularTvShowAdapter(){
        popularTvShowAdapter.setClickListener(this)
        lifecycleScope.launch {
            viewModel.popularTvShow.collectLatest {
                popularTvShowAdapter.submitData(it)
            }
        }
    }

    private fun initPopularMoviesAdapter(){
        popularMovieAdapter.setListener(this)
        lifecycleScope.launch {
            viewModel.popularMovies.collectLatest {
                popularMovieAdapter.submitData(it)
            }
        }
    }

    private fun initOuterAdapter(){
        val items = listOf(HomeItem("", trendAdapter),
        HomeItem(getString(R.string.now_streaming_movies_item), streamingMovieAdapter),
        HomeItem(getString(R.string.popular_tv_show_item), popularTvShowAdapter),
        HomeItem(getString(R.string.popular_movies_item), popularMovieAdapter))
        outerAdapter = OuterAdapter(items)
        outerAdapter.setListener(this)
    }

    private fun initHomeList(){
        binder!!.homeList.apply {
            adapter = outerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
                .create()
                .inject(this)
    }

    private fun observeConnection(){
        viewModel.connectionLiveData.observe(viewLifecycleOwner, Observer { isConnected ->
            if (!isConnected){
                Toast.makeText(requireContext(), getString(R.string.conection_lost), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemSelected(id: Int, contentType: Int) {
        when(contentType){
            ItemClickListener.MovieType -> {
                Log.e("Movie", "Was Selected -> id: $id")
                val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(id)
                findNavController().navigate(action)
            }
            ItemClickListener.TvShowType -> {
                Log.e("TvShow", "Was Selected -> id: $id")
                val action = HomeFragmentDirections.actionHomeFragmentToTvDetailsFragment(id)
                findNavController().navigate(action)
            }
            ItemClickListener.PersonType -> {
                Log.e("Person", "Was Selected -> id: $id")
                val action = HomeFragmentDirections.actionHomeFragmentToPersonDetailsFragment(id)
                findNavController().navigate(action)
            }
            ItemClickListener.SearchType -> {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binder = null
    }

}