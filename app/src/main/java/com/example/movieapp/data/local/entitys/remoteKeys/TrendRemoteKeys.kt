package com.example.movieapp.data.local.entitys.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trend_remote_keys")
data class TrendRemoteKeys(
        @PrimaryKey
        val id:Int,
        val prevKey:Int?,
        val nextKey:Int?
)
