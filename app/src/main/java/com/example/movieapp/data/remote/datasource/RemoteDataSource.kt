package com.example.movieapp.data.remote.datasource

import com.example.movieapp.data.model.cast.CastResponse
import com.example.movieapp.data.model.credits.MovieCreditsResponse
import com.example.movieapp.data.model.credits.TvCreditsResponse
import com.example.movieapp.data.model.genre.GenreResponse
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.model.people.PeopleDetails
import com.example.movieapp.data.model.search.SearchResponse
import com.example.movieapp.data.model.similarMovies.SimilarMoviesResponse
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
        TvGenreSource,
        MovieCastSource,
        MovieDetailsSource,
        MovieVideosSource,
        SimilarMoviesSource,
        PeopleDetailsSource,
        MovieCreditsSource,
        TvCreditsSource,
        SearchDataSource
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

    override suspend fun getMovieCast(movie_id: Int, language: String): LoadStatus<CastResponse> {
        return saveApiCall { apiService.getMovieCast(movie_id, language) }
    }

    override suspend fun getMovieDetails(movie_id: Int, language: String): LoadStatus<MovieDetailsResponse> {
        return saveApiCall { apiService.getMovieDetails(movie_id, language) }
    }

    override suspend fun getMovieVideos(movie_id: Int, language: String): LoadStatus<MovieVideoResponse> {
        return saveApiCall { apiService.getMovieVideos(movie_id, language) }
    }

    override suspend fun getSimilarMovies(movie_id: Int, language: String): LoadStatus<SimilarMoviesResponse> {
        return saveApiCall { apiService.getSimilarMovies(movie_id, language) }
    }

    override suspend fun getPersonDetails(personId: Int, language: String): LoadStatus<PeopleDetails> {
        return saveApiCall { apiService.getPersonDetails(personId, language) }
    }

    override suspend fun getMovieCredits(personId: Int, language: String): LoadStatus<MovieCreditsResponse> {
        return saveApiCall { apiService.getMovieCredits(personId, language) }
    }

    override suspend fun getTvCredits(personId: Int, language: String): LoadStatus<TvCreditsResponse> {
        return saveApiCall { apiService.getTvCredits(personId, language) }
    }

    override suspend fun searchQuery(
        query: String,
        page: Int,
        language: String
    ): LoadStatus<SearchResponse> {
        return saveApiCall { apiService.searchQuery(query, page, language) }
    }
}