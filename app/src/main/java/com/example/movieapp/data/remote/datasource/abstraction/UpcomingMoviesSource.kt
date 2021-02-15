package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.remote.LoadStatus
import retrofit2.http.Query

interface UpcomingMoviesSource {

    suspend fun getUpcomingMovies(
        @Query("language") language:String = "en-US", // uk-UK Українська
        @Query("page") page:Int = 1
    ): LoadStatus<MoviesResponse>
}