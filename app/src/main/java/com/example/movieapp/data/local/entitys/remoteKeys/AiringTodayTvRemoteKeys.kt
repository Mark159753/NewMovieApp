package com.example.movieapp.data.local.entitys.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airing_today_tv_remote_key")
data class AiringTodayTvRemoteKeys(
    @PrimaryKey
    val id:Int,
    val prevKey:Int?,
    val nextKey:Int?
)
