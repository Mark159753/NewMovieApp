package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.cast.CastResponse
import com.example.movieapp.data.remote.LoadStatus

interface MovieCastSource {

    suspend fun getMovieCast(
            movie_id:Int,
            language:String = "en-US"
    ): LoadStatus<CastResponse>
}