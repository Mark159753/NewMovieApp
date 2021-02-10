package com.example.movieapp.domain.mappers

import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.domain.model.MovieData

class MovieMapper:IMapper<Movie, MovieData> {

    override fun map(item: Movie): MovieData {
        return MovieData(
                item.adult,
                item.backdrop_path,
                item.genre_ids,
                item.id,
                item.original_language,
                item.original_title,
                item.overview,
                item.popularity,
                item.poster_path,
                item.release_date,
                item.title,
                item.video,
                item.vote_average,
                item.vote_count
        )
    }
}