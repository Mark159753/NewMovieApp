package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.local.dao.*
import com.example.movieapp.data.local.entitys.*
import com.example.movieapp.data.local.entitys.remoteKeys.NowStreamingRemoteKeys
import com.example.movieapp.data.local.entitys.remoteKeys.PopularMoviesRemoteKeys
import com.example.movieapp.data.local.entitys.remoteKeys.PopularTvShowRemoteKeys
import com.example.movieapp.data.local.entitys.remoteKeys.TrendRemoteKeys
import com.example.movieapp.data.local.typeConverters.ListIntConverter
import com.example.movieapp.data.local.typeConverters.ListStringConverter

@Database(entities = [
    Movie::class,
    Trends::class,
    TvShow::class,
    NowStreamingMovieEntity::class,
    PopularTvShowsEntity::class,
    PopularMovieEntity::class,
    NowStreamingRemoteKeys::class,
    PopularMoviesRemoteKeys::class,
    PopularTvShowRemoteKeys::class,
    TrendRemoteKeys::class
], version = 14, exportSchema = false)
@TypeConverters(
    ListIntConverter::class,
    ListStringConverter::class
)
abstract class MovieDb:RoomDatabase() {

    abstract fun getTrendsDao():TrendsDao
    abstract fun getNowStreamingDao():NowStreamingMoviesDao
    abstract fun getMovieDao():MovieDao
    abstract fun getPopularTvShowDao():PopularTvShowDao
    abstract fun getTvShowDao():TvShowDao
    abstract fun getPopularMovieDao():PopularMovieDao

    abstract fun getTrendsRemoteKeysDao():TrendRemoteKeysDao
    abstract fun getNowStreamingMoviesRemoteKeysDao():NowStreamingRemoteKeysDao
    abstract fun getPopularTvShowsRemoteKeysDao():PopularTvSHowRemoteKeysDao
    abstract fun getPopularMoviesRemoteKeysDao():PopularMoviesRemoteKeysDao
}