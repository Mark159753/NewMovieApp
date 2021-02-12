package com.example.movieapp.di.modules

import com.example.movieapp.data.repository.HomeRepositoryImpl
import com.example.movieapp.data.repository.TrendRepositoryImpl
import com.example.movieapp.di.components.FragmentScope
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
}