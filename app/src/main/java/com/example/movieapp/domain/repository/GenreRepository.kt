package com.example.movieapp.domain.repository

import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.data.local.entitys.genre.TvGenre


interface GenreRepository {

    suspend fun getMovieGenre(language: String):List<MovieGenre>?

    suspend fun getTvGenre(language: String):List<TvGenre>?
}