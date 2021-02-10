package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.NowStreamingMovieEntity

@Dao
interface NowStreamingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:NowStreamingMovieEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<NowStreamingMovieEntity>)

    @Query("SELECT movie_tab.* FROM movie_tab INNER JOIN now_streaming_movie ON now_streaming_movie.movieId = movie_tab.id")
    suspend fun getAllMovies():List<Movie>

    @Transaction
    @Query("SELECT movie_tab.* FROM movie_tab INNER JOIN now_streaming_movie ON now_streaming_movie.movieId = movie_tab.id ORDER BY now_streaming_movie.id ASC")
    fun getAllMoviesPagingSource():PagingSource<Int, Movie>

    @Transaction
    @Query("DELETE FROM now_streaming_movie")
    suspend fun deleteAll()
}