package com.example.movieapp.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_tab")
data class TvShow(
        val backdrop_path: String?,
        val first_air_date: String?,
        val genre_ids: List<Int>?,
        @PrimaryKey
        val id: Int,
        val name: String?,
        val origin_country: List<String>?,
        val original_language: String?,
        val original_name: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val vote_average: Double?,
        val vote_count: Int?
)