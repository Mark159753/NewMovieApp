package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.genre.GenreResponse
import com.example.movieapp.data.remote.LoadStatus

interface MovieGenreSource {

    suspend fun getGenreMovieList(
           language:String = "en-US"
    ): LoadStatus<GenreResponse>
}