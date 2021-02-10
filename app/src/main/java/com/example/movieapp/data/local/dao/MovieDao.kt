package com.example.movieapp.data.local.dao

import androidx.room.*
import com.example.movieapp.data.local.entitys.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:Movie)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<Movie>)

    @Query("SELECT * FROM movie_tab")
    suspend fun getAllMovies():List<Movie>

    @Query("DELETE FROM movie_tab")
    suspend fun deleteAll()
}