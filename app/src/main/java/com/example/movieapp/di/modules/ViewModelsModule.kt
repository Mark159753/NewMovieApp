package com.example.movieapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.di.AssistedSavedStateViewModelFactory
import com.example.movieapp.di.ViewModelFactoryDI
import com.example.movieapp.di.ViewModelKey
import com.example.movieapp.ui.comingSoon.ComingSoonViewModel
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.ui.details.movie.MovieDetailsViewModel
import com.example.movieapp.ui.trends.TrendsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactoryDI): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(model: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComingSoonViewModel::class)
    abstract fun bindComingSoonViewModel(model: ComingSoonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(model: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrendsViewModel::class)
    abstract fun bindTrendsViewModel(f: TrendsViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}