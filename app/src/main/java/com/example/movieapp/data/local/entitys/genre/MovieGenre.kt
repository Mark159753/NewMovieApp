package com.example.movieapp.data.local.entitys.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genre_tab")
data class MovieGenre(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val language: String
)