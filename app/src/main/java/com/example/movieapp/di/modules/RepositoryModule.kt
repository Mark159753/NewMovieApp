package com.example.movieapp.di.modules

import com.example.movieapp.data.repository.HomeRepositoryImpl
import com.example.movieapp.di.components.FragmentScope
import com.example.movieapp.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @FragmentScope
    abstract fun bindHomeRepository(repository: HomeRepositoryImpl):HomeRepository
}