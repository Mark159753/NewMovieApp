package com.example.movieapp.data.model.trending

import com.example.movieapp.data.local.entitys.Trends

data class TrendsResponse(
        val page: Int?,
        val results: List<Trends>?,
        val total_pages: Int?,
        val total_results: Int?
)