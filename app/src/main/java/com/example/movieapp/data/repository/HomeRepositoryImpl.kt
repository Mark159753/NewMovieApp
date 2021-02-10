package com.example.movieapp.data.repository

import androidx.paging.*
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.data.local.dao.*
import com.example.movieapp.data.paging.NowStreamingMoviesMediator
import com.example.movieapp.data.paging.PopularMovieMediator
import com.example.movieapp.data.paging.PopularTvShowMediator
import com.example.movieapp.data.paging.TrendsRemoteMediator
import com.example.movieapp.data.remote.datasource.abstraction.MoviePopularSource
import com.example.movieapp.data.remote.datasource.abstraction.MovieTrendsSource
import com.example.movieapp.data.remote.datasource.abstraction.NowStreamingMoviesSource
import com.example.movieapp.data.remote.datasource.abstraction.PopularTvShowSource
import com.example.movieapp.domain.mappers.MovieMapper
import com.example.movieapp.domain.mappers.TrendsMapper
import com.example.movieapp.domain.mappers.TvShowMapper
import com.example.movieapp.domain.model.MovieData
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.domain.model.TvShowData
import com.example.movieapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
        private val db: MovieDb,
        private val trendsDao: TrendsDao,
        private val remoteTrendsSource: MovieTrendsSource,
        private val remoteStreamingMoviesSource:NowStreamingMoviesSource,
        private val streamingMoviesDao: NowStreamingMoviesDao,
        private val moviesDao: MovieDao,
        private val popularTvShowSource: PopularTvShowSource,
        private val popularTvShowDao:PopularTvShowDao,
        private val tvShowDao: TvShowDao,
        private val moviePopularSource: MoviePopularSource,
        private val moviePopularDao: PopularMovieDao
):HomeRepository {

    @ExperimentalPagingApi
    override fun getTrends(
        mediaType:String,
        timeWindow:String,
        language:String
    ): Flow<PagingData<TrendsData>> {
        val mapper = TrendsMapper()
        return Pager(
                config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false
                ),
                remoteMediator = TrendsRemoteMediator(db, trendsDao,
                    remoteTrendsSource,
                    mediaType,
                    timeWindow,
                    language),
                pagingSourceFactory = { trendsDao.getPagingSource() }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    @ExperimentalPagingApi
    override fun getNowStreamingMovies(language: String): Flow<PagingData<MovieData>> {
        val mapper = MovieMapper()
        return Pager(
                config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false
                ),
                remoteMediator = NowStreamingMoviesMediator(db, remoteStreamingMoviesSource, streamingMoviesDao, moviesDao, language),
                pagingSourceFactory = { streamingMoviesDao.getAllMoviesPagingSource() }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    @ExperimentalPagingApi
    override fun getPopularTvShows(language: String): Flow<PagingData<TvShowData>> {
        val mapper = TvShowMapper()
        return Pager(
                config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false
                ),
                remoteMediator = PopularTvShowMediator(db, popularTvShowSource, popularTvShowDao, tvShowDao, language),
                pagingSourceFactory = { popularTvShowDao.getAllTvShowsPagingSource() }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    @ExperimentalPagingApi
    override fun getPopularMovies(language: String): Flow<PagingData<MovieData>> {
        val mapper = MovieMapper()
        return Pager(
                config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false
                ),
                remoteMediator = PopularMovieMediator(db, moviePopularSource, moviePopularDao, moviesDao, language),
                pagingSourceFactory = { moviePopularDao.getAllMoviesPagingSource() }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    companion object{
        private const val PAGE_SIZE = 20
    }
}