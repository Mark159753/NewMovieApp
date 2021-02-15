package com.example.movieapp.data.local.dao

import androidx.room.*
import com.example.movieapp.data.local.entitys.remoteKeys.*

@Dao
interface TrendRemoteKeysDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: TrendRemoteKeys)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<TrendRemoteKeys>)

    @Query("SELECT * FROM trend_remote_keys")
    suspend fun getAllItems():List<TrendRemoteKeys>

    @Query("SELECT * FROM trend_remote_keys WHERE id = :id")
    suspend fun getItemById(id:Int): TrendRemoteKeys?

    @Query("DELETE FROM trend_remote_keys")
    suspend fun deleteAll()
}

@Dao
interface NowStreamingRemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: NowStreamingRemoteKeys)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<NowStreamingRemoteKeys>)

    @Query("SELECT * FROM now_streaming_remote_keys")
    suspend fun getAllItems():List<NowStreamingRemoteKeys>

    @Transaction
    @Query("SELECT * FROM now_streaming_remote_keys WHERE id = :id")
    suspend fun getItemById(id:Int): NowStreamingRemoteKeys?

    @Transaction
    @Query("DELETE FROM now_streaming_remote_keys")
    suspend fun deleteAll()
}


@Dao
interface PopularMoviesRemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PopularMoviesRemoteKeys)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<PopularMoviesRemoteKeys>)

    @Query("SELECT * FROM popular_movies_remote_keys")
    suspend fun getAllItems():List<PopularMoviesRemoteKeys>

    @Transaction
    @Query("SELECT * FROM popular_movies_remote_keys WHERE id = :id")
    suspend fun getItemById(id:Int): PopularMoviesRemoteKeys?

    @Transaction
    @Query("DELETE FROM popular_movies_remote_keys")
    suspend fun deleteAll()
}

@Dao
interface PopularTvSHowRemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PopularTvShowRemoteKeys)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<PopularTvShowRemoteKeys>)

    @Query("SELECT * FROM popular_tv_show_remote_keys")
    suspend fun getAllItems():List<PopularTvShowRemoteKeys>

    @Query("SELECT * FROM popular_tv_show_remote_keys WHERE id = :id")
    suspend fun getItemById(id:Int): PopularTvShowRemoteKeys?

    @Query("DELETE FROM popular_tv_show_remote_keys")
    suspend fun deleteAll()
}

@Dao
interface AiringTodayTvRemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: AiringTodayTvRemoteKeys)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<AiringTodayTvRemoteKeys>)

    @Query("SELECT * FROM airing_today_tv_remote_key")
    suspend fun getAllItems():List<AiringTodayTvRemoteKeys>

    @Query("SELECT * FROM airing_today_tv_remote_key WHERE id = :id")
    suspend fun getItemById(id:Int): AiringTodayTvRemoteKeys?

    @Query("DELETE FROM airing_today_tv_remote_key")
    suspend fun deleteAll()
}

@Dao
interface UpcomingMoviesRemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: UpcomingMoviesRemoteKeys)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<UpcomingMoviesRemoteKeys>)

    @Query("SELECT * FROM upcoming_movies_remote_keys_tab")
    suspend fun getAllItems():List<UpcomingMoviesRemoteKeys>

    @Query("SELECT * FROM upcoming_movies_remote_keys_tab WHERE id = :id")
    suspend fun getItemById(id:Int): UpcomingMoviesRemoteKeys?

    @Query("DELETE FROM upcoming_movies_remote_keys_tab")
    suspend fun deleteAll()
}