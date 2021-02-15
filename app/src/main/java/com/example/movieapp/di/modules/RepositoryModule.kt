package com.example.movieapp.di.modules

import com.example.movieapp.data.repository.ComingRepositoryImpl
import com.example.movieapp.data.repository.GenreRepositoryImpl
import com.example.movieapp.data.repository.HomeRepositoryImpl
import com.example.movieapp.data.repository.TrendRepositoryImpl
import com.example.movieapp.di.components.FragmentScope
import com.example.movieapp.domain.repository.ComingRepository
import com.example.movieapp.domain.repository.GenreRepository
import com.example.movieapp.domain.repository.HomeRepository
import com.example.movieapp.domain.repository.TrendRepository
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
}