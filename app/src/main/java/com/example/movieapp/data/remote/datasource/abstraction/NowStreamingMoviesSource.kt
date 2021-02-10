package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.remote.LoadStatus

interface NowStreamingMoviesSource {

    suspend fun getNowStreamingMovies(
            language:String = "en-US", // uk-UK Українська
            page:Int = 1
    ): LoadStatus<MoviesResponse>
}