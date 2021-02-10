package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entitys.PopularTvShowsEntity
import com.example.movieapp.data.local.entitys.TvShow

@Dao
interface PopularTvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PopularTvShowsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<PopularTvShowsEntity>)

    @Query("SELECT tv_show_tab.* FROM tv_show_tab INNER JOIN popular_tv_shows_tab ON popular_tv_shows_tab.showId = tv_show_tab.id")
    suspend fun getAllTvShows():List<TvShow>

    @Query("SELECT tv_show_tab.* FROM tv_show_tab INNER JOIN popular_tv_shows_tab ON popular_tv_shows_tab.showId = tv_show_tab.id ORDER BY popular_tv_shows_tab.id ASC")
    fun getAllTvShowsPagingSource(): PagingSource<Int, TvShow>

    @Query("DELETE FROM popular_tv_shows_tab")
    suspend fun deleteAll()
}