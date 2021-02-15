package com.example.movieapp.data.local.entitys.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies_remote_keys_tab")
data class UpcomingMoviesRemoteKeys(
    @PrimaryKey
    val id:Int,
    val prevKey:Int?,
    val nextKey:Int?
)
