package com.example.movieapp.data.repository

import androidx.paging.*
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.data.local.dao.AiringTodayTvDao
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.dao.TvShowDao
import com.example.movieapp.data.local.dao.UpcomingMoviesDao
import com.example.movieapp.data.paging.AiringTodayTvMediator
import com.example.movieapp.data.paging.UpcomingMoviesMediator
import com.example.movieapp.data.remote.datasource.abstraction.AiringTodayTvShowSource
import com.example.movieapp.data.remote.datasource.abstraction.UpcomingMoviesSource
import com.example.movieapp.domain.mappers.MovieMapper
import com.example.movieapp.domain.mappers.TvShowMapper
import com.example.movieapp.domain.model.MovieData
import com.example.movieapp.domain.model.TvShowData
import com.example.movieapp.domain.repository.ComingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ComingRepositoryImpl @Inject constructor(
    private val db: MovieDb,
    private val upcomingMoviesSource: UpcomingMoviesSource,
    private val airingTodayTvShowSource: AiringTodayTvShowSource,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val upcomingMoviesDao: UpcomingMoviesDao,
    private val airingTodayTvDao: AiringTodayTvDao
):ComingRepository {

    @ExperimentalPagingApi
    override fun getAiringTodayTvShow(language: String): Flow<PagingData<TvShowData>> {
        val mapper = TvShowMapper()
        return Pager(
            config = PagingConfig(
                pageSize = LOADING_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = AiringTodayTvMediator(db, airingTodayTvShowSource, airingTodayTvDao, tvShowDao, language),
            pagingSourceFactory = { airingTodayTvDao.getAllTvShowPagingSource() }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    @ExperimentalPagingApi
    override fun getUpcomingMovies(language: String): Flow<PagingData<MovieData>> {
        val mapper = MovieMapper()
        return Pager(
            config = PagingConfig(
                pageSize = LOADING_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = UpcomingMoviesMediator(db, upcomingMoviesSource, upcomingMoviesDao, movieDao, language),
            pagingSourceFactory = { upcomingMoviesDao.getAllMoviesPagingSource() }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    companion object{
        private const val LOADING_SIZE = 20
    }
}