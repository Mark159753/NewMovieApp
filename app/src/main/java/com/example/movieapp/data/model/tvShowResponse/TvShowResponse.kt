package com.example.movieapp.data.model.tvShowResponse

import com.example.movieapp.data.local.entitys.TvShow

data class TvShowResponse(
        val page: Int?,
        val results: List<TvShow>?,
        val total_pages: Int?,
        val total_results: Int?
)