package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.UpcomingMoviesEntity

@Dao
interface UpcomingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UpcomingMoviesEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<UpcomingMoviesEntity>)

    @Query("SELECT movie_tab.* FROM movie_tab INNER JOIN upcoming_movies_tab ON upcoming_movies_tab.movieId = movie_tab.id")
    suspend fun getAllMovies():List<Movie>

    @Transaction
    @Query("SELECT movie_tab.* FROM movie_tab INNER JOIN upcoming_movies_tab ON upcoming_movies_tab.movieId = movie_tab.id ORDER BY upcoming_movies_tab.id ASC")
    fun getAllMoviesPagingSource(): PagingSource<Int, Movie>

    @Transaction
    @Query("DELETE FROM now_streaming_movie")
    suspend fun deleteAll()
}