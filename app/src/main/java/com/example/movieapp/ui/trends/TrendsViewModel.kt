package com.example.movieapp.ui.trends

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.R
import com.example.movieapp.di.AssistedSavedStateViewModelFactory
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.domain.repository.TrendRepository
import com.example.movieapp.ui.trends.state.State
import com.example.movieapp.ui.trends.state.TrendAction
import com.example.movieapp.ui.trends.state.TrendEvent
import com.example.movieapp.ui.trends.state.TrendQueryParameters
import com.example.movieapp.until.ConnectionLiveData
import com.example.movieapp.until.LocaleHelper
import com.example.movieapp.until.TimeWindow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class TrendsViewModel @AssistedInject constructor(
        private val repository: TrendRepository,
        private val context: Context,
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val actions = MutableSharedFlow<TrendAction>()
    private val _states = MutableStateFlow<State>(State.Loading)

    private var trendPaging:Flow<PagingData<TrendsData>>? = null

    private val _events = MutableSharedFlow<TrendEvent>()
    val events:SharedFlow<TrendEvent>
        get() = _events

    val state:StateFlow<State>
        get() = _states

    val connectivityLiveData = ConnectionLiveData(context)

    init {
        handleActions()
        initAction()
    }

    private fun initAction(){
        val restoreState = savedStateHandle.get<TrendQueryParameters>(TREND_STATE_SAVE)
        restoreState?.let {
            viewModelScope.launch { setState(State.DataSuccess(null, it)) }
            setAction(TrendAction.FetchData(it))
        } ?: setAction(TrendAction.FetchData(TrendQueryParameters.createTimeWindowParameters(TimeWindow.Day, null)))
    }

    fun setAction(action: TrendAction){
        viewModelScope.launch {
            actions.emit(action)
        }
    }

    private suspend fun setEvent(e:TrendEvent){
        _events.emit(e)
    }

    private suspend fun setState(state:State){
        _states.emit(state)
    }

    private fun handleActions(){
        viewModelScope.launch {
            actions.collect {action ->
                when(action){
                    TrendAction.LostInternetConnection -> {
                        setEvent(TrendEvent.ShowToast(context.resources.getString(R.string.conection_lost)))
                    }
                    is TrendAction.FetchData -> {
                        setState(State.Loading)
                       val data = getTrendPagingData(action.parameters)
                        setState(State.DataSuccess(data, action.parameters))
                    }
                    TrendAction.NoInternetConnection -> {
                        setState(State.NoInternetConnection)
                    }
                }
            }
        }
    }

    private fun getTrendPagingData(queryParameters: TrendQueryParameters): Flow<PagingData<TrendsData>> {
        trendPaging = repository
                .getTrends(queryParameters.trendContentType.type, queryParameters.trendTimeWindow.timeWindow, LocaleHelper.getLanguage(context))
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
        return trendPaging!!
        }

    private fun saveState(){
        if (_states.value is State.DataSuccess){
            savedStateHandle.set(TREND_STATE_SAVE, (_states.value as State.DataSuccess).queryParameters)
        }
    }

    override fun onCleared() {
        saveState()
        super.onCleared()
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TrendsViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): TrendsViewModel
    }

    companion object{
        private const val TREND_STATE_SAVE = "com.example.movieapp.ui.trends.TREND_ACTION_SAVE"
    }

}