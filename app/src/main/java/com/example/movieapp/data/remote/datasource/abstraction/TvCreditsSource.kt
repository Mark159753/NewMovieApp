package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.credits.TvCreditsResponse
import com.example.movieapp.data.remote.LoadStatus

interface TvCreditsSource {

    suspend fun getTvCredits(
            personId:Int,
            language:String = "en-US"
    ): LoadStatus<TvCreditsResponse>
}