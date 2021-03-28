package com.example.movieapp.ui.details.person

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.TvShow
import com.example.movieapp.data.model.people.PeopleDetails
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.MovieCreditsSource
import com.example.movieapp.data.remote.datasource.abstraction.PeopleDetailsSource
import com.example.movieapp.data.remote.datasource.abstraction.TvCreditsSource
import com.example.movieapp.ui.details.person.state.Action
import com.example.movieapp.ui.details.person.state.Data
import com.example.movieapp.ui.details.person.state.State
import com.example.movieapp.until.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(
        private val context: Context,
        private val peopleDetailsSource: PeopleDetailsSource,
        private val movieCreditsSource: MovieCreditsSource,
        private val tvCreditsSource: TvCreditsSource
) : ViewModel() {

    private var peopleDetails:PeopleDetails? = null
    private var movieCredits:List<Movie>? = null
    private var tvCredits:List<TvShow>? = null

    private val _state:MutableStateFlow<State> = MutableStateFlow(State.LoadingState)
    val state:StateFlow<State>
    get() = _state

    private val _action:MutableSharedFlow<Action> = MutableSharedFlow()
    val action:SharedFlow<Action>
    get() = _action

    fun setAction(action: Action){
        viewModelScope.launch {
            _action.emit(action)
        }
    }

    private suspend fun setState(state: State){
        _state.emit(state)
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
                    Action.NoInternetConnection -> {
                        setState(State.NoInternetConnection)
                    }
                }
            }
        }
    }

    private fun loadData(id:Int){
        viewModelScope.launch {
            val details = async(Dispatchers.IO){
                loadPersonDetails(id)
            }
            val movieCredits = async(Dispatchers.IO){
                loadMovieCredits(id)
            }
            val tvCredits = async(Dispatchers.IO){
                loadTvCredits(id)
            }
            setState(State.DataState(Data(details.await(), movieCredits.await(), tvCredits.await())))
        }
    }

    private suspend fun loadPersonDetails(id:Int):PeopleDetails?{
        if (peopleDetails == null){
            peopleDetails = when (val res = peopleDetailsSource.getPersonDetails(id, LocaleHelper.getLanguage(context))) {
                            is LoadStatus.Error -> null
                            is LoadStatus.Success -> res.data
                }
        }
        return peopleDetails
    }

    private suspend fun loadMovieCredits(id:Int):List<Movie>?{
        if (movieCredits == null){
            movieCredits = when (val res = movieCreditsSource.getMovieCredits(id, LocaleHelper.getLanguage(context))) {
                is LoadStatus.Error -> null
                is LoadStatus.Success -> res.data.cast
            }
        }
        return movieCredits
    }

    private suspend fun loadTvCredits(id:Int):List<TvShow>?{
        if (tvCredits == null){
            tvCredits = when (val res = tvCreditsSource.getTvCredits(id, LocaleHelper.getLanguage(context))) {
                is LoadStatus.Error -> null
                is LoadStatus.Success -> res.data.cast
            }
        }
        return tvCredits
    }
}