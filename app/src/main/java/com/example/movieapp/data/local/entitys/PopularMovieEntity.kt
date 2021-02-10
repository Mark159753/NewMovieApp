package com.example.movieapp.data.local.entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_tab", foreignKeys = [
    ForeignKey(entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.NO_ACTION,
            deferred = true)
])
data class PopularMovieEntity(
        val movieId:Int,
        @PrimaryKey(autoGenerate = true)
        val id:Long? = null
)
