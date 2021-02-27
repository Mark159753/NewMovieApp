package com.example.movieapp.di.modules

import com.example.movieapp.data.repository.*
import com.example.movieapp.di.components.FragmentScope
import com.example.movieapp.domain.repository.*
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @FragmentScope
    abstract fun bindHomeRepository(repository: HomeRepositoryImpl):HomeRepository

    @Binds
    @FragmentScope
    abstract fun bindTrendRepository(repository: TrendRepositoryImpl):TrendRepository

    @Binds
    @FragmentScope
    abstract fun bindComingRepository(repository: ComingRepositoryImpl):ComingRepository

    @Binds
    @FragmentScope
    abstract fun bindGenreRepository(repository: GenreRepositoryImpl):GenreRepository

    @Binds
    @FragmentScope
    abstract fun bindMovieDetailsRepository(repository: MovieDetailsRepositoryImpl):MovieDetailsRepository
}