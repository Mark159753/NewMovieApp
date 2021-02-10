package com.example.movieapp.data.model.moviesResponse

import com.example.movieapp.data.local.entitys.Movie

data class MoviesResponse(
    val page: Int?,
    val results: List<Movie>?,
    val total_pages: Int?,
    val total_results: Int?
)