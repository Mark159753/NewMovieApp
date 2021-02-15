package com.example.movieapp.data.local.entitys.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_genre_tab")
data class TvGenre(
        @PrimaryKey
        val id: Int,
        val name: String?,
        val language: String
)
