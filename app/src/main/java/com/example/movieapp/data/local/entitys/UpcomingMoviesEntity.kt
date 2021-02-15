package com.example.movieapp.data.local.entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies_tab", foreignKeys = [
    ForeignKey(entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.NO_ACTION)
])
data class UpcomingMoviesEntity(
    val movieId:Int,
    @PrimaryKey(autoGenerate = true)
    val id:Long? = null
)
