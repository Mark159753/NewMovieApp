package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.data.model.cast.CastResponse
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import com.example.movieapp.data.remote.LoadStatus
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun getMovieDetails(
        movie_id:Int,
        language:String = "en-US"
    ):LoadStatus<MovieDetailsResponse>

    suspend fun getMovieCast(movie_id: Int, language: String): LoadStatus<CastResponse>

    suspend fun getMovieVideos(movie_id: Int, language: String): LoadStatus<MovieVideoResponse>

    fun getSimilarMovies(movie_id: Int, language: String): Flow<PagingData<SimilarMovie>>
}