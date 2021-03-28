package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.credits.MovieCreditsResponse
import com.example.movieapp.data.remote.LoadStatus

interface MovieCreditsSource {

    suspend fun getMovieCredits(
            personId:Int,
            language:String = "en-US"
    ): LoadStatus<MovieCreditsResponse>
}