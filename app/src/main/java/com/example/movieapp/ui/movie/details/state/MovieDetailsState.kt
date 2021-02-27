package com.example.movieapp.ui.movie.details.state

import androidx.paging.PagingData
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import kotlinx.coroutines.flow.Flow

data class Data(
        val movieDetails: MovieDetailsResponse?,
        val movieCast:List<Cast>?,
        val movieVideos:MovieVideoResponse?,
        val similarMovies: Flow<PagingData<SimilarMovie>>?
)

sealed class State{
    object LoadingState:State()
    data class DataState(val data: Data):State()
    data class ErrorState(val msg:String):State()
}

sealed class Event{
    data class ShowDialog(val msg: String):Event()
    data class ShowToast(val msg:String):Event()
    data class PlayYoutubeVideo(val key:String):Event()
}

sealed class Action{
    object Load:Action()
    object NoInternetConnection:Action()
    data class PlayYouTubeVideo(val key: String):Action()
}
