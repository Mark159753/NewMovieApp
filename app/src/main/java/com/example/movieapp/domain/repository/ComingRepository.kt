package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.domain.model.MovieData
import com.example.movieapp.domain.model.TvShowData
import kotlinx.coroutines.flow.Flow

interface ComingRepository {

    fun getAiringTodayTvShow(language: String): Flow<PagingData<TvShowData>>

    fun getUpcomingMovies(language: String):Flow<PagingData<MovieData>>
}