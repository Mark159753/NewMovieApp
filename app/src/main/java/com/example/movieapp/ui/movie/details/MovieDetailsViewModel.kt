package com.example.movieapp.ui.movie.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.domain.repository.MovieDetailsRepository
import com.example.movieapp.until.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    context: Context,
    private val repository: MovieDetailsRepository
): ViewModel() {

    private var movieDetails:MovieDetailsResponse? = null
    private var movieCast:List<Cast>? = null
    private var videos: MovieVideoResponse? = null

    private var similarMovie: Flow<PagingData<SimilarMovie>>? = null

    val connectivityLiveData = ConnectionLiveData(context)

    suspend fun loadDetails(id:Int):MovieDetailsResponse?{
        if (movieDetails != null) return movieDetails
        val res = repository.getMovieDetails(id, "en-US")
        when(res){
            is LoadStatus.Success -> {
                movieDetails = res.data
                return movieDetails!!
            }
            is LoadStatus.Error -> {
                return null
            }
        }
    }

    suspend fun loadMovieCast(id:Int):List<Cast>?{
        if (movieCast != null) return movieCast
        val res = repository.getMovieCast(id, "en-US")
        when(res){
            is LoadStatus.Success -> {
                movieCast = res.data.cast
                return movieCast
            }
            is LoadStatus.Error -> {
                return null
            }
        }
    }

    suspend fun loadVideos(id: Int):MovieVideoResponse?{
        if (videos != null) return videos
        val res = repository.getMovieVideos(id, "en-US")
        when(res){
            is LoadStatus.Success -> {
                videos = res.data
                return videos
            }
            is LoadStatus.Error -> {
                return null
            }
        }
    }

    fun getSimilarMovies(id:Int):Flow<PagingData<SimilarMovie>>{
        if (similarMovie != null) return similarMovie!!
        similarMovie = repository.getSimilarMovies(id, "en-US")
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
        return similarMovie!!
    }

}