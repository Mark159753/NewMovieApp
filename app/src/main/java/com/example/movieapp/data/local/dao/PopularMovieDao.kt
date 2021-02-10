package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.PopularMovieEntity

@Dao
interface PopularMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PopularMovieEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<PopularMovieEntity>)

    @Query("SELECT movie_tab.* FROM movie_tab INNER JOIN popular_movies_tab ON popular_movies_tab.movieId = movie_tab.id")
    suspend fun getAllMovies():List<Movie>

    @Transaction
    @Query("SELECT movie_tab.* FROM movie_tab INNER JOIN popular_movies_tab ON popular_movies_tab.movieId = movie_tab.id ORDER BY popular_movies_tab.id ASC")
    fun getAllMoviesPagingSource(): PagingSource<Int, Movie>

    @Transaction
    @Query("DELETE FROM popular_movies_tab")
    suspend fun deleteAll()
}