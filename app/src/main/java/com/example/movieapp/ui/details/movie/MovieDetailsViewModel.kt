package com.example.movieapp.ui.details.movie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.R
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.domain.repository.MovieDetailsRepository
import com.example.movieapp.ui.details.movie.state.Action
import com.example.movieapp.ui.details.movie.state.Data
import com.example.movieapp.ui.details.movie.state.Event
import com.example.movieapp.ui.details.movie.state.State
import com.example.movieapp.until.ConnectionLiveData
import com.example.movieapp.until.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val context: Context,
    private val repository: MovieDetailsRepository
): ViewModel() {

    private var movieDetails:MovieDetailsResponse? = null
    private var movieCast:List<Cast>? = null
    private var videos: MovieVideoResponse? = null

    private var similarMovie: Flow<PagingData<SimilarMovie>>? = null

    val connectivityLiveData = ConnectionLiveData(context)

    private val _state = MutableStateFlow<State>(State.LoadingState)
    val state:StateFlow<State>
        get() = _state

    private val _event = MutableSharedFlow<Event>()
    val event:SharedFlow<Event>
        get() = _event

    private val action = MutableSharedFlow<Action>()

    fun setAction(action: Action){
        viewModelScope.launch {
            this@MovieDetailsViewModel.action.emit(action)
        }
    }

    private suspend fun setState(state: State){
        _state.emit(state)
    }

    private suspend fun setEvent(event: Event){
        _event.emit(event)
    }

    init {
        handleAction()
    }

    private fun handleAction(){
        viewModelScope.launch {
            action.collect {
                when(it){
                    is Action.Load -> {
                        setState(State.LoadingState)
                        loadData(it.id)
                    }
                    Action.LostInternetConnection -> {
                        setEvent(Event.ShowToast(context.resources.getString(R.string.conection_lost)))
                    }
                    Action.NoInternetConnection -> {
                        setState(State.NoInternetConnection)
                    }
                    is Action.PlayYouTubeVideo -> {
                        setEvent(Event.PlayYoutubeVideo(getVideoKey(it.id)))
                    }
                }
            }
        }
    }

    private suspend fun getVideoKey(id: Int):String?{
        val videos = loadVideos(id)
        videos?.results?.let { list ->
            list.forEach {
                if (it.site == "YouTube") return it.key
            }
        }
        return null
    }

    private fun loadData(id: Int){
        viewModelScope.launch {
            val details = async(Dispatchers.IO){
                loadDetails(id)
            }
            val cast = async(Dispatchers.IO){
                loadMovieCast(id)
            }
            val similar = getSimilarMovies(id)
            setState(State.DataState(Data(details.await(), cast.await(), similar)))
        }
    }

    private suspend fun loadDetails(id:Int):MovieDetailsResponse?{
        if (movieDetails != null) return movieDetails
        val res = repository.getMovieDetails(id, LocaleHelper.getLanguage(context))
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

    private suspend fun loadMovieCast(id:Int):List<Cast>?{
        if (movieCast != null) return movieCast
        val res = repository.getMovieCast(id, LocaleHelper.getLanguage(context))
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

    private suspend fun loadVideos(id: Int):MovieVideoResponse?{
        if (videos != null) return videos
        val res = repository.getMovieVideos(id, LocaleHelper.getLanguage(context))
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

    private fun getSimilarMovies(id:Int):Flow<PagingData<SimilarMovie>>{
        if (similarMovie != null) return similarMovie!!
        similarMovie = repository.getSimilarMovies(id, LocaleHelper.getLanguage(context))
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
        return similarMovie!!
    }

}