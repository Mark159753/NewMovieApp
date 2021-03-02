package com.example.movieapp.ui.details.movie

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import androidx.navigation.fragment.navArgs
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieDetailsFragmentBinding
import com.example.movieapp.ui.details.movie.adapter.*
import com.example.movieapp.ui.details.movie.state.Action
import com.example.movieapp.ui.details.movie.state.Data
import com.example.movieapp.ui.details.movie.state.Event
import com.example.movieapp.ui.details.movie.state.State
import com.example.movieapp.until.isInternetAvailable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsFragment : Fragment(), ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieDetailsViewModel by viewModels{ viewModelFactory }

    private val castAdapter = CastAdapter()
    private val similarMovieAdapter = SimilarMovieAdapter()
    private val movieDetailsAdapter = MovieDetailsAdapter()

    val args: MovieDetailsFragmentArgs by navArgs()

    private var binding:MovieDetailsFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectMe()

        initToolbar()
        initAllAdapters()
        observeConnectivity()

        handleState()
        handleEvent()
        if (isInternetAvailable(requireContext())) {
            viewModel.setAction(Action.Load(args.movieId))
        }else{
            viewModel.setAction(Action.NoInternetConnection)
        }
    }


    private fun handleState(){
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it){
                    State.LoadingState -> {
                        showLoading(true)
                    }
                    State.NoInternetConnection -> {
                        showNoInternetConnectionMsg()
                    }
                    is State.DataState -> {
                        showLoading(false)
                        setLoadedData(it.data)
                    }
                }
            }
        }
    }

    private fun handleEvent(){
        lifecycleScope.launch {
            viewModel.event.collect {
                when(it){
                    is Event.ShowToast -> {
                        showToast(it.msg)
                    }
                    is Event.PlayYoutubeVideo -> {
                        it.key?.let { k -> startYoutube(k) } ?: showToast(getString(R.string.cant_find_video))
                    }
                }
            }
        }
    }


    private fun initAllAdapters(){
        similarMovieAdapter.setListener(this)
        castAdapter.setListener(this)
        movieDetailsAdapter.setListener(this)
        initContentList()
    }

    private fun setLoadedData(data: Data) {
        lifecycleScope.launch {
            data.similarMovies.collectLatest {
                similarMovieAdapter.submitData(it)
            }
        }
        data.movieCast?.let { castAdapter.setDataList(it) }
        data.movieDetails?.let {
            binding!!.movieDetailsToolbar.title = it.title
            movieDetailsAdapter.setData(MovieDetailsData(it, castAdapter, similarMovieAdapter))
        }
    }


    private fun initContentList(){
        binding!!.movieDetailsContentList.apply {
            adapter = movieDetailsAdapter
            setHasFixedSize(true)
        }
    }


    private fun startYoutube(key:String){
        val youTubeIntent = Intent(Intent.ACTION_VIEW,
        Uri.parse("https://www.youtube.com/watch?v=$key"))
        try {
            startActivity(youTubeIntent)
        }catch (e: ActivityNotFoundException){
            Log.e("Not Fount Activity", "Can't play youtube video")
        }
    }

    private fun initToolbar(){
        binding!!.movieDetailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun itemClicked(type: Int, id: Int) {
        when(type){
            ItemClickListener.MOVIE_TYPE ->{
                Log.e("MOVIE ->", "CLICK")
                val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(id)
                findNavController().navigate(action)
            }
            ItemClickListener.PERSON_TYPE -> {
                Log.e("PERSON ->", "CLICK")
                val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(id)
                findNavController().navigate(action)
            }
            ItemClickListener.VIDEO_TYPE -> {
                viewModel.setAction(Action.PlayYouTubeVideo(args.movieId))
            }
        }
    }

    private fun showToast(msg:String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
                .create()
                .inject(this)
    }



    private fun showNoInternetConnectionMsg(){
        binding!!.apply {
            movieDetailsToolbar.visibility = View.VISIBLE
            movieDetailsContentList.visibility = View.GONE
            movieDetailsLoading.visibility = View.GONE
            movieDetailsNoConnection.visibility = View.VISIBLE
        }
    }

    private fun showLoading(show:Boolean){
        if (show){
            binding!!.apply {
                movieDetailsToolbar.visibility = View.VISIBLE
                movieDetailsContentList.visibility = View.GONE
                movieDetailsLoading.visibility = View.VISIBLE
                movieDetailsNoConnection.visibility = View.GONE
            }
        }else{
            binding!!.apply {
                movieDetailsToolbar.visibility = View.VISIBLE
                movieDetailsContentList.visibility = View.VISIBLE
                movieDetailsLoading.visibility = View.GONE
                movieDetailsNoConnection.visibility = View.GONE
            }
        }
    }

    private fun observeConnectivity(){
        viewModel.connectivityLiveData.observe(viewLifecycleOwner, Observer {
            if (it){
                viewModel.setAction(Action.Load(args.movieId))
            }else{
                viewModel.setAction(Action.LostInternetConnection)
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}