package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.tvShowResponse.TvShowResponse
import com.example.movieapp.data.remote.LoadStatus

interface PopularTvShowSource {

    suspend fun getPopularTvShows(
            language:String = "en-US", // uk-UK Українська
            page:Int = 1
    ): LoadStatus<TvShowResponse>
}