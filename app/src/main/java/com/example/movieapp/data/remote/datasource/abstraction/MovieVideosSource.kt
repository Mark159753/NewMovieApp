package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.remote.LoadStatus

interface MovieVideosSource {

    suspend fun getMovieVideos(
            movie_id:Int,
            language:String = "en-US"
    ): LoadStatus<MovieVideoResponse>

}