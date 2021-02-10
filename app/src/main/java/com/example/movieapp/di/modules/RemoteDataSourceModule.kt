package com.example.movieapp.di.modules

import com.example.movieapp.data.remote.datasource.RemoteDataSource
import com.example.movieapp.data.remote.datasource.abstraction.MoviePopularSource
import com.example.movieapp.data.remote.datasource.abstraction.MovieTrendsSource
import com.example.movieapp.data.remote.datasource.abstraction.NowStreamingMoviesSource
import com.example.movieapp.data.remote.datasource.abstraction.PopularTvShowSource
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
}