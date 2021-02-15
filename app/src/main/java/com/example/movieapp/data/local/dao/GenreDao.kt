package com.example.movieapp.data.local.dao

import androidx.room.*
import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.data.local.entitys.genre.TvGenre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenre(item:MovieGenre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvGenre(item:TvGenre)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovieGenre(item:List<MovieGenre>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTvGenre(item:List<TvGenre>)


    @Query("SELECT * FROM movie_genre_tab")
    suspend fun getAllMovieGenre():List<MovieGenre>

    @Query("SELECT * FROM tv_genre_tab")
    suspend fun getAllTvGenre():List<TvGenre>


    @Query("DELETE FROM movie_genre_tab")
    suspend fun deleteAllMovieGenre()

    @Query("DELETE FROM tv_genre_tab")
    suspend fun deleteAllFromTvGenre()
}