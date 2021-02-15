package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.local.dao.*
import com.example.movieapp.data.local.entitys.*
import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.data.local.entitys.genre.TvGenre
import com.example.movieapp.data.local.entitys.remoteKeys.*
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
    TrendRemoteKeys::class,
    AiringTodayTvRemoteKeys::class,
    UpcomingMoviesRemoteKeys::class,
    AiringTodayTvShowEntity::class,
    UpcomingMoviesEntity::class,
    MovieGenre::class,
    TvGenre::class
], version = 16, exportSchema = false)
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
    abstract fun getAiringTodayTvDao():AiringTodayTvDao
    abstract fun getUpcomingMoviesDao():UpcomingMoviesDao

    abstract fun getTrendsRemoteKeysDao():TrendRemoteKeysDao
    abstract fun getNowStreamingMoviesRemoteKeysDao():NowStreamingRemoteKeysDao
    abstract fun getPopularTvShowsRemoteKeysDao():PopularTvSHowRemoteKeysDao
    abstract fun getPopularMoviesRemoteKeysDao():PopularMoviesRemoteKeysDao
    abstract fun getUpcomingMoviesRemoteKeysDao():UpcomingMoviesRemoteKeysDao
    abstract fun getAiringTodayTvRemoteKeysDao():AiringTodayTvRemoteKeysDao

    abstract fun getGenreDao():GenreDao
}