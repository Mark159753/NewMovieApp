package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.similarMovies.SimilarMoviesResponse
import com.example.movieapp.data.remote.LoadStatus

interface SimilarMoviesSource {

    suspend fun getSimilarMovies(
            movie_id:Int,
            language:String = "en-US"
    ): LoadStatus<SimilarMoviesResponse>
}