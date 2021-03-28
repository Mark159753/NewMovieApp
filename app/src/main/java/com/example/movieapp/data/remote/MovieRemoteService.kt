package com.example.movieapp.data.remote

import com.example.movieapp.data.model.cast.CastResponse
import com.example.movieapp.data.model.credits.MovieCreditsResponse
import com.example.movieapp.data.model.credits.TvCreditsResponse
import com.example.movieapp.data.model.genre.GenreResponse
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.model.people.PeopleDetails
import com.example.movieapp.data.model.similarMovies.SimilarMoviesResponse
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

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language:String = "en-US", // uk-UK Українська
        @Query("page") page:Int = 1
    ):Response<MoviesResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShow(
        @Query("language") language:String = "en-US", // uk-UK Українська
        @Query("page") page:Int = 1
    ):Response<TvShowResponse>

    @GET("genre/movie/list")
    suspend fun getGenreMovieList(
        @Query("language") language:String = "en-US" // uk-UK Українська
    ):Response<GenreResponse>

    @GET("genre/tv/list")
    suspend fun getGenreTvList(
            @Query("language") language:String = "en-US" // uk-UK Українська
    ):Response<GenreResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
            @Path("movie_id") movie_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
            @Path("movie_id") movie_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<SimilarMoviesResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
            @Path("movie_id") movie_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<MovieVideoResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
            @Path("movie_id") movie_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<CastResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
            @Path("person_id") person_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<PeopleDetails>

    @GET("person/{person_id}/movie_credits")
    suspend fun getMovieCredits(
            @Path("person_id") person_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<MovieCreditsResponse>

    @GET("person/{person_id}/tv_credits")
    suspend fun getTvCredits(
            @Path("person_id") person_id:Int,
            @Query("language") language:String = "en-US"
    ):Response<TvCreditsResponse>
}