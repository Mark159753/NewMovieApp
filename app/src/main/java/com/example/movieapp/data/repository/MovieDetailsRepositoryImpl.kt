package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.model.cast.CastResponse
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.data.model.movieVideo.MovieVideoResponse
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import com.example.movieapp.data.paging.SimilarMoviesPagingDataSource
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.MovieCastSource
import com.example.movieapp.data.remote.datasource.abstraction.MovieDetailsSource
import com.example.movieapp.data.remote.datasource.abstraction.MovieVideosSource
import com.example.movieapp.data.remote.datasource.abstraction.SimilarMoviesSource
import com.example.movieapp.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val detailsSource: MovieDetailsSource,
    private val castSource: MovieCastSource,
    private val videosSource: MovieVideosSource,
    private val similarMoviesSource: SimilarMoviesSource
):MovieDetailsRepository {

    override suspend fun getMovieDetails(
        movie_id: Int,
        language: String
    ): LoadStatus<MovieDetailsResponse> {
        return detailsSource.getMovieDetails(movie_id, language)
    }

    override suspend fun getMovieCast(movie_id: Int, language: String): LoadStatus<CastResponse> {
        return castSource.getMovieCast(movie_id, language)
    }

    override suspend fun getMovieVideos(movie_id: Int, language: String): LoadStatus<MovieVideoResponse> {
        return videosSource.getMovieVideos(movie_id, language)
    }

    override fun getSimilarMovies(movie_id: Int, language: String): Flow<PagingData<SimilarMovie>> {
        return Pager(
                config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { SimilarMoviesPagingDataSource(similarMoviesSource, movie_id, language) }
        ).flow
    }
}