package com.example.movieapp.domain.mappers

import com.example.movieapp.data.local.entitys.TvShow
import com.example.movieapp.domain.model.TvShowData

class TvShowMapper:IMapper<TvShow, TvShowData> {

    override fun map(item: TvShow): TvShowData {
        return TvShowData(
                item.backdrop_path,
                item.first_air_date,
                item.genre_ids,
                item.id,
                item.name,
                item.origin_country,
                item.original_language,
                item.original_name,
                item.overview,
                item.popularity,
                item.poster_path,
                item.vote_average,
                item.vote_count
        )
    }
}