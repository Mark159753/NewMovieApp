package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.trending.TrendsResponse
import com.example.movieapp.data.remote.LoadStatus

interface MovieTrendsSource {

    suspend fun getMovieTrendsSource(
            media_type:String = "movie",
            time_window:String = "day",
            language:String = "en-US", // uk-UK Українська
            page:Int = 1
    ):LoadStatus<TrendsResponse>
}