package com.example.movieapp.ui.home.listeners

interface ItemClickListener {

    fun onItemSelected(id:Int, contentType:Int)

    companion object{
        const val MovieType = 0
        const val TvShowType = 1
        const val PersonType = 2
    }
}