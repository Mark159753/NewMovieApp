package com.example.movieapp.domain.mappers

import com.example.movieapp.data.local.entitys.Trends
import com.example.movieapp.domain.model.TrendsData

class TrendsMapper:IMapper<Trends, TrendsData> {

    override fun map(item: Trends): TrendsData {
        return TrendsData(
                item.adult,
                item.backdrop_path,
                item.first_air_date,
                item.genre_ids,
                item.id,
                item.media_type,
                item.name,
                item.origin_country,
                item.original_language,
                item.original_name,
                item.original_title,
                item.overview,
                item.popularity,
                item.poster_path,
                item.release_date,
                item.title,
                item.video,
                item.vote_average,
                item.vote_count,
                item.gender,
                item.known_for_department,
                item.profile_path
        )
    }
}