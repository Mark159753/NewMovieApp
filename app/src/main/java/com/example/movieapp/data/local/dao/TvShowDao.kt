package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.TvShow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TvShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<TvShow>)

    @Query("SELECT * FROM tv_show_tab")
    suspend fun getAllTvShows():List<TvShow>

    @Query("DELETE FROM tv_show_tab")
    suspend fun deleteAll()
}