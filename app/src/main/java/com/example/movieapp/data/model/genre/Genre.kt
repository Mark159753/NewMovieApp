package com.example.movieapp.data.model.genre

import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.data.local.entitys.genre.TvGenre

data class Genre(
        val id: Int,
        val name: String?
){

    companion object{
        fun mapToMovieGenre(item:Genre, language: String):MovieGenre{
            return MovieGenre(item.id, item.name, language)
        }

        fun mapToTvGenre(item:Genre, language: String):TvGenre{
            return TvGenre(item.id, item.name, language)
        }
    }
}