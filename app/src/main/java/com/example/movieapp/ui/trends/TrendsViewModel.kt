package com.example.movieapp.ui.trends

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.di.AssistedSavedStateViewModelFactory
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.domain.repository.TrendRepository
import com.example.movieapp.ui.trends.state.TrendAction
import com.example.movieapp.ui.trends.state.TrendQueryParameters
import com.example.movieapp.ui.trends.state.TrendUiState
import com.example.movieapp.until.TimeWindow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class TrendsViewModel @AssistedInject constructor(
        private val repository: TrendRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val actions = MutableSharedFlow<TrendAction>()
    private val _states = MutableStateFlow<TrendUiState>(TrendUiState.initState())

    private var trendPaging:Flow<PagingData<TrendsData>>? = null

    val state:StateFlow<TrendUiState>
        get() = _states

    init {
        handleActions()
        initAction()
    }

    private fun initAction(){
        val restoreState = savedStateHandle.get<TrendQueryParameters>(TREND_STATE_SAVE)
        restoreState?.let {
            viewModelScope.launch { _states.emit(TrendUiState(null, it)) }
            if (it.trendTimeWindow == TimeWindow.Day)
                setAction(TrendAction.TrendTimeWindow(TimeWindow.Day, "en-us"))
            else
                setAction(TrendAction.TrendTimeWindow(TimeWindow.Week, "en-us"))
        } ?: setAction(TrendAction.TrendTimeWindow(TimeWindow.Day, "en-us"))
    }

    fun setAction(action: TrendAction){
        viewModelScope.launch {
            actions.emit(action)
        }
    }

    private fun handleActions(){
        viewModelScope.launch {
            actions.collect {action ->
                when(action){
                    is TrendAction.TrendTimeWindow -> {
                        val parameter = TrendQueryParameters(action.trendTimeWindow, _states.value.queryParameters.trendContentType, action.language)
                        val paging = getTrendPagingData(parameter)
                        _states.emit(TrendUiState(paging, parameter))
                    }
                    is TrendAction.TrendContentType -> {
                        val parameter = TrendQueryParameters(_states.value.queryParameters.trendTimeWindow, action.mediaTypes, action.language)
                        val paging = getTrendPagingData(parameter)
                        _states.emit(TrendUiState(paging, parameter))
                    }
                }
            }
        }
    }

    private fun getTrendPagingData(queryParameters: TrendQueryParameters): Flow<PagingData<TrendsData>> {
        trendPaging = repository
                .getTrends(queryParameters.trendContentType.type, queryParameters.trendTimeWindow.timeWindow, queryParameters.language)
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
        return trendPaging!!
        }

    private fun saveAction(){
        savedStateHandle.set(TREND_STATE_SAVE, _states.value.queryParameters)
    }

    override fun onCleared() {
        saveAction()
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