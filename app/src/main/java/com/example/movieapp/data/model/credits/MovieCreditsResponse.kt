package com.example.movieapp.data.model.credits

import com.example.movieapp.data.local.entitys.Movie

data class MovieCreditsResponse(
    val cast: List<Movie>?,
    val id: Int?
)