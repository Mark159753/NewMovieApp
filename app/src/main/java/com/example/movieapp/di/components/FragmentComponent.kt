package com.example.movieapp.di.components

import com.example.movieapp.di.modules.RepositoryModule
import com.example.movieapp.di.modules.ViewModelsModule
import com.example.movieapp.ui.home.HomeFragment
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

    @Subcomponent.Factory
    interface Factory{
        fun create():FragmentComponent
    }
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope