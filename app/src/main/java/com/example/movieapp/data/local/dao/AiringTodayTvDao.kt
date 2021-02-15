package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.data.local.entitys.AiringTodayTvShowEntity
import com.example.movieapp.data.local.entitys.TvShow

@Dao
interface AiringTodayTvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AiringTodayTvShowEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<AiringTodayTvShowEntity>)

    @Query("SELECT tv_show_tab.* FROM tv_show_tab INNER JOIN airing_today_tv_show ON airing_today_tv_show.tvShowId = tv_show_tab.id")
    suspend fun getAllTvShows():List<TvShow>

    @Transaction
    @Query("SELECT tv_show_tab.* FROM tv_show_tab INNER JOIN airing_today_tv_show ON airing_today_tv_show.tvShowId = tv_show_tab.id ORDER BY airing_today_tv_show.id ASC")
    fun getAllTvShowPagingSource(): PagingSource<Int, TvShow>

    @Transaction
    @Query("DELETE FROM airing_today_tv_show")
    suspend fun deleteAll()
}