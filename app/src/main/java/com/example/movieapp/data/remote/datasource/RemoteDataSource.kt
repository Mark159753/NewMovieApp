package com.example.movieapp.data.remote.datasource

import com.example.movieapp.data.model.genre.GenreResponse
import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.model.trending.TrendsResponse
import com.example.movieapp.data.model.tvShowResponse.TvShowResponse
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.MovieRemoteService
import com.example.movieapp.data.remote.datasource.abstraction.*
import com.example.movieapp.data.remote.saveApiCall
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(private val apiService: MovieRemoteService)
        :MoviePopularSource,
        MovieTrendsSource,
        NowStreamingMoviesSource,
        PopularTvShowSource,
        UpcomingMoviesSource,
        AiringTodayTvShowSource,
        MovieGenreSource,
        TvGenreSource
{

    override suspend fun getMovieTrendsSource(media_type: String, time_window: String, language: String, page: Int): LoadStatus<TrendsResponse> {
        return saveApiCall { apiService.getMovieTrends(media_type, time_window, language, page) }
    }

    override suspend fun getPopularMovies(language: String, page: Int): LoadStatus<MoviesResponse> {
        return saveApiCall { apiService.getPopularMovies(language, page) }
    }

    override suspend fun getNowStreamingMovies(language: String, page: Int): LoadStatus<MoviesResponse> {
        return saveApiCall { apiService.getNowStreamingMovies(language, page) }
    }

    override suspend fun getPopularTvShows(language: String, page: Int): LoadStatus<TvShowResponse> {
        return saveApiCall { apiService.getPopularTvShows(language, page) }
    }

    override suspend fun getUpcomingMovies(
        language: String,
        page: Int
    ): LoadStatus<MoviesResponse> {
        return saveApiCall { apiService.getUpcomingMovies(language, page) }
    }

    override suspend fun getAiringTodayTvShow(
        language: String,
        page: Int
    ): LoadStatus<TvShowResponse> {
        return saveApiCall { apiService.getPopularTvShows(language, page) }
    }

    override suspend fun getGenreMovieList(language: String): LoadStatus<GenreResponse> {
        return saveApiCall { apiService.getGenreMovieList(language) }
    }

    override suspend fun getGenreTvList(language: String): LoadStatus<GenreResponse> {
        return saveApiCall { apiService.getGenreTvList(language) }
    }
}