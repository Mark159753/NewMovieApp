package com.example.movieapp

import android.app.Application
import com.example.movieapp.di.components.ApplicationComponent
import com.example.movieapp.di.components.DaggerApplicationComponent

class MovieApp:Application() {

    private lateinit var applicationComponent:ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }


    fun getFragmentComponent() = applicationComponent.getFragmentSubComponentFactory()

}