package com.example.movieapp.data.local.entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "popular_tv_shows_tab", foreignKeys = [
    ForeignKey(
            entity = TvShow::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.NO_ACTION
    )
])
data class PopularTvShowsEntity(
        val showId:Int,
        @PrimaryKey(autoGenerate = true)
        val id:Long? = null
)
