package com.example.movieapp.di.components

import android.content.Context
import com.example.movieapp.di.modules.DatabaseModule
import com.example.movieapp.di.modules.NetworkServiceModule
import com.example.movieapp.di.modules.RemoteDataSourceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            DatabaseModule::class,
            NetworkServiceModule::class,
            RemoteDataSourceModule::class
        ]
)
interface ApplicationComponent {

    fun getFragmentSubComponentFactory():FragmentComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context):ApplicationComponent
    }

}