package com.example.movieapp.ui.details.movie.state

import androidx.paging.PagingData
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import kotlinx.coroutines.flow.Flow

data class Data(
        val movieDetails: MovieDetailsResponse?,
        val movieCast:List<Cast>?,
        val similarMovies: Flow<PagingData<SimilarMovie>>
)

sealed class State{
    object LoadingState: State()
    object NoInternetConnection:State()
    data class DataState(val data: Data): State()
}

sealed class Event{
    data class ShowToast(val msg:String): Event()
    data class PlayYoutubeVideo(val key:String?): Event()
}

sealed class Action{
    data class Load(val id:Int): Action()
    object LostInternetConnection:Action()
    object NoInternetConnection: Action()
    data class PlayYouTubeVideo(val id: Int): Action()
}
