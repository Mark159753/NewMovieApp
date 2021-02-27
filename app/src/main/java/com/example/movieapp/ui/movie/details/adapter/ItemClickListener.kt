package com.example.movieapp.ui.movie.details.adapter

interface ItemClickListener {

    fun itemClicked(type:Int, id:Int)

    companion object{
        const val MOVIE_TYPE = 1
        const val PERSON_TYPE = 2
        const val VIDEO_TYPE = 3
    }
}