package com.example.movieapp.data.model.similarMovies

data class SimilarMoviesResponse(
        val page: Int?,
        val results: List<SimilarMovie>?,
        val total_pages: Int?,
        val total_results: Int?
)