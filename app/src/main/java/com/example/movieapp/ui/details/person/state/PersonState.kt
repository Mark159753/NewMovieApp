package com.example.movieapp.ui.details.person.state

import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.TvShow
import com.example.movieapp.data.model.people.PeopleDetails

data class Data(
        val details: PeopleDetails?,
        val movieCredits:List<Movie>?,
        val tvCredits:List<TvShow>?
)

sealed class State{
    object LoadingState: State()
    object NoInternetConnection:State()
    data class DataState(val data: Data): State()
}

sealed class Action{
    data class Load(val id:Int): Action()
    object NoInternetConnection: Action()
}