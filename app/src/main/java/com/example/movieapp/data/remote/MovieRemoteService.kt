package com.example.movieapp.data.remote

import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.model.trending.TrendsResponse
import com.example.movieapp.data.model.tvShowResponse.TvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRemoteService {

    @GET("trending/{media_type}/{time_window}")
    suspend fun getMovieTrends(
            @Path("media_type") media_type:String = "movie",
            @Path("time_window") time_window:String = "day",
            @Query("language") language:String = "en-US", // uk-UK Українська
            @Query("page") page:Int = 1
    ):Response<TrendsResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
            @Query("language") language:String = "en-US", // uk-UK Українська
            @Query("page") page:Int = 1
    ):Response<MoviesResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
            @Query("language") language:String = "en-US", // uk-UK Українська
            @Query("page") page:Int = 1
    ):Response<TvShowResponse>

    @GET("movie/now_playing")
    suspend fun getNowStreamingMovies(
            @Query("language") language:String = "en-US", // uk-UK Українська
            @Query("page") page:Int = 1
    ):Response<MoviesResponse>
}