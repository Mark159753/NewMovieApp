package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.remote.LoadStatus

interface MovieDetailsSource {

    suspend fun getMovieDetails(
            movie_id:Int,
            language:String = "en-US"
    ): LoadStatus<MovieDetailsResponse>
}