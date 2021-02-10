package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.remote.LoadStatus

interface MoviePopularSource {

    suspend fun getPopularMovies(
            language:String = "en-US", // uk-UK Українська
            page:Int = 1
    ): LoadStatus<MoviesResponse>
}