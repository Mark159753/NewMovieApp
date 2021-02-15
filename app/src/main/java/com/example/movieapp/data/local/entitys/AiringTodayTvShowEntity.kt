package com.example.movieapp.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airing_today_tv_show", foreignKeys = [
        androidx.room.ForeignKey(
            entity = TvShow::class,
            parentColumns = ["id"],
            childColumns = ["tvShowId"],
            onDelete = androidx.room.ForeignKey.NO_ACTION
        )
])
data class AiringTodayTvShowEntity(
    val tvShowId:Int,
    @PrimaryKey(autoGenerate = true)
    val id:Long? = null
)
