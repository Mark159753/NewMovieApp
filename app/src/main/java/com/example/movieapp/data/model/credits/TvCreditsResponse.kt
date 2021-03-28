package com.example.movieapp.data.model.credits

import com.example.movieapp.data.local.entitys.TvShow

data class TvCreditsResponse(
    val cast: List<TvShow>?,
    val id: Int?
)