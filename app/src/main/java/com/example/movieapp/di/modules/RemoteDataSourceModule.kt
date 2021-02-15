package com.example.movieapp.di.modules

import com.example.movieapp.data.remote.datasource.RemoteDataSource
import com.example.movieapp.data.remote.datasource.abstraction.*
import dagger.Binds
import dagger.Module

@Module
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideMoviePopularSource(source: RemoteDataSource): MoviePopularSource

    @Binds
    abstract fun provideMovieTrendsSource(source: RemoteDataSource): MovieTrendsSource

    @Binds
    abstract fun provideNowStreamingMoviesSource(source: RemoteDataSource): NowStreamingMoviesSource

    @Binds
    abstract fun providePopularTvShowSource(source: RemoteDataSource): PopularTvShowSource

    @Binds
    abstract fun provideUpcomingMoviesSource(source: RemoteDataSource): UpcomingMoviesSource

    @Binds
    abstract fun provideAiringTodayTvShowSource(source: RemoteDataSource): AiringTodayTvShowSource

    @Binds
    abstract fun bindMovieGenreSource(source: RemoteDataSource): MovieGenreSource

    @Binds
    abstract fun bindTvGenreSource(source: RemoteDataSource): TvGenreSource
}