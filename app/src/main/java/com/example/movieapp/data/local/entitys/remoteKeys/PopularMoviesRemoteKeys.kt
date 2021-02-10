package com.example.movieapp.data.local.entitys.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_remote_keys")
data class PopularMoviesRemoteKeys(
        @PrimaryKey
        val id:Int,
        val prevKey:Int?,
        val nextKey:Int?
)
