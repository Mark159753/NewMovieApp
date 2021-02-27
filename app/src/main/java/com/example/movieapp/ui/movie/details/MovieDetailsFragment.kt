package com.example.movieapp.ui.movie.details

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
import com.example.movieapp.data.model.movieVideo.Result
import com.example.movieapp.databinding.MovieDetailsFragmentBinding
import com.example.movieapp.ui.movie.details.adapter.*
import com.example.movieapp.until.isInternetAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsFragment : Fragment(), ItemClickListener{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieDetailsViewModel by viewModels{ viewModelFactory }

    private val castAdapter = CastAdapter()
    private val similarMovieAdapter = SimilarMovieAdapter()
    private val movieDetailsAdapter = MovieDetailsAdapter()

    val args:MovieDetailsFragmentArgs by navArgs()

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
        observeConnectivity()
        if (isInternetAvailable(requireContext())) {
            showLoading(true)

            initAllAdapters()
        }else{
            showNoInternetConnectionMsg()
        }
    }

    private fun initAllAdapters(){
        initCastAdapter()
        initSimilarMovieAdapter()
        initContentAdapter()
        initContentList()
    }

    private fun initSimilarMovieAdapter(){
        similarMovieAdapter.setListener(this)
        lifecycleScope.launch {
            viewModel.getSimilarMovies(args.movieId).collectLatest {
                similarMovieAdapter.submitData(it)
            }
        }
    }

    private fun initCastAdapter(){
        castAdapter.setListener(this)
        lifecycleScope.launch {
            val cast = viewModel.loadMovieCast(args.movieId)
            cast?.let { castAdapter.setDataList(it) }
        }
    }

    private fun initContentAdapter(){
        movieDetailsAdapter.setListener(this)
        lifecycleScope.launch {
            val details = withContext(Dispatchers.IO){
                viewModel.loadDetails(args.movieId)
            }
            binding!!.movieDetailsToolbar.title = details?.title
            details?.let {
                showLoading(false)
                movieDetailsAdapter.setData(MovieDetailsData(it, castAdapter, similarMovieAdapter))
            }
        }
    }

    private fun initContentList(){
        binding!!.movieDetailsContentList.apply {
            adapter = movieDetailsAdapter
            setHasFixedSize(true)
        }
    }


    private fun getVideoKey(list:List<Result>):String?{
        list.forEach {
            if (it.site == "YouTube") return it.key
        }
        return null
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
            }
            ItemClickListener.PERSON_TYPE -> {
                Log.e("PERSON ->", "CLICK")
            }
            ItemClickListener.VIDEO_TYPE -> {
                lifecycleScope.launch {
                    val videos = viewModel.loadVideos(args.movieId)
                    val key = videos?.let { getVideoKey(it.results!!) }
                    key?.let { startYoutube(it) } ?: showToast(getString(R.string.cant_find_video))
                }
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
                movieDetailsToolbar.visibility = View.GONE
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
                showLoading(true)
                initAllAdapters()
            }else{
                showToast(getString(R.string.conection_lost))
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}