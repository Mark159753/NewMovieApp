package com.example.movieapp.data.local.dao

import androidx.room.*
import com.example.movieapp.data.local.entitys.remoteKeys.NowStreamingRemoteKeys
import com.example.movieapp.data.local.entitys.remoteKeys.PopularMoviesRemoteKeys
import com.example.movieapp.data.local.entitys.remoteKeys.PopularTvShowRemoteKeys
import com.example.movieapp.data.local.entitys.remoteKeys.TrendRemoteKeys

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