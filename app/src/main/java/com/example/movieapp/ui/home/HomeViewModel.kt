package com.example.movieapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.domain.repository.HomeRepository
import com.example.movieapp.until.ConnectionLiveData
import com.example.movieapp.until.MediaTypes
import com.example.movieapp.until.TimeWindow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        repository: HomeRepository,
        context: Context
) : ViewModel() {

    val connectionLiveData = ConnectionLiveData(context)

    val trend = repository
            .getTrends(MediaTypes.All.type, TimeWindow.Day.timeWindow, "en-us")
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    val nowStreamingMovies = repository
            .getNowStreamingMovies("en-us")
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    val popularTvShow = repository
            .getPopularTvShows("en-us")
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    val popularMovies = repository
            .getPopularMovies("en-us")
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
}