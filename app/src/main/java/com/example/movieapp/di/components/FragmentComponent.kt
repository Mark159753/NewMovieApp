package com.example.movieapp.di.components

import com.example.movieapp.di.modules.RepositoryModule
import com.example.movieapp.di.modules.ViewModelsModule
import com.example.movieapp.ui.comingSoon.ComingMoviesFragment
import com.example.movieapp.ui.comingSoon.ComingSoonFragment
import com.example.movieapp.ui.comingSoon.ComingTvShowFragment
import com.example.movieapp.ui.home.HomeFragment
import com.example.movieapp.ui.trends.TrendsFragment
import dagger.Subcomponent
import javax.inject.Scope

@FragmentScope
@Subcomponent(
        modules = [
            ViewModelsModule::class,
            RepositoryModule::class
        ]
)
interface FragmentComponent {

    fun inject(fragment:HomeFragment)
    fun inject(fragment:TrendsFragment)
    fun inject(fragment:ComingMoviesFragment)
    fun inject(fragment:ComingTvShowFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create():FragmentComponent
    }
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope