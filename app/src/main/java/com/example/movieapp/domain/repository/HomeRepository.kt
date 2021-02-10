package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.domain.model.MovieData
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.domain.model.TvShowData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getTrends(mediaType:String, timeWindow:String, language:String):Flow<PagingData<TrendsData>>

    fun getNowStreamingMovies(language: String):Flow<PagingData<MovieData>>

    fun getPopularTvShows(language: String):Flow<PagingData<TvShowData>>

    fun getPopularMovies(language: String):Flow<PagingData<MovieData>>
}