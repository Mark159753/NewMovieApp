package com.example.movieapp.ui.comingSoon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.data.local.entitys.genre.TvGenre
import com.example.movieapp.domain.repository.ComingRepository
import com.example.movieapp.domain.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComingSoonViewModel @Inject constructor(
    repository: ComingRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {

    private var movieGenreList:List<MovieGenre>? = null
    private var tvGenreList:List<TvGenre>? = null

    val movies = repository.getUpcomingMovies("en-us")
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    val tv = repository.getAiringTodayTvShow("en-us")
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    suspend fun getMovieGenre(): List<MovieGenre>? {
        movieGenreList?.let { return@let it }
        movieGenreList = genreRepository.getMovieGenre("en-us")
        return movieGenreList
    }

    suspend fun getTvShowGenre():List<TvGenre>?{
        tvGenreList?.let { return@let it }
        tvGenreList = genreRepository.getTvGenre("en-us")
        return tvGenreList
    }
}