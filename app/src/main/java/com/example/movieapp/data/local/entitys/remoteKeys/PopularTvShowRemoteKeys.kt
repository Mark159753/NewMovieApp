package com.example.movieapp.data.local.entitys.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_tv_show_remote_keys")
data class PopularTvShowRemoteKeys(
        @PrimaryKey
        val id:Int,
        val prevKey:Int?,
        val nextKey:Int?
)
