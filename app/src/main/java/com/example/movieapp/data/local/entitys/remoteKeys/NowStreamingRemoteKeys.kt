package com.example.movieapp.data.local.entitys.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_streaming_remote_keys")
data class NowStreamingRemoteKeys(
        @PrimaryKey
        val id:Int,
        val prevKey:Int?,
        val nextKey:Int?
)
