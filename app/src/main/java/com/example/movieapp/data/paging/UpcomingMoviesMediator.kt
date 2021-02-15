package com.example.movieapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.dao.UpcomingMoviesDao
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.data.local.entitys.UpcomingMoviesEntity
import com.example.movieapp.data.local.entitys.remoteKeys.UpcomingMoviesRemoteKeys
import com.example.movieapp.data.model.moviesResponse.MoviesResponse
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.UpcomingMoviesSource
import com.example.movieapp.until.UNKNOWN_ERROR
import java.io.IOException

@ExperimentalPagingApi
class UpcomingMoviesMediator(
    private val db: MovieDb,
    private val remoteSource:UpcomingMoviesSource,
    private val upcomingMoviesDao: UpcomingMoviesDao,
    private val moviesDao: MovieDao,
    private val language:String
):RemoteMediator<Int, Movie>() {

    private val remoteKeysDao = db.getUpcomingMoviesRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
//                        ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                var remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey ?: 1
            }
        }

        val apiResponse = apiCall(page)
        when(apiResponse){
            is LoadStatus.Success -> {
                return saveData(apiResponse.data, loadType, page)
            }
            is LoadStatus.Error -> {
                return MediatorResult.Error(apiResponse.e ?: IOException(UNKNOWN_ERROR))
            }
            else -> {
                val err = apiResponse as LoadStatus.Error
                return MediatorResult.Error(err.e ?: IOException(UNKNOWN_ERROR))
            }
        }
    }

    private suspend fun saveData(apiResponse: MoviesResponse, loadType: LoadType, page: Int): MediatorResult {
        val items = apiResponse.results
        val endOfPaging = items!!.isEmpty()
        db.withTransaction {
            if (loadType == LoadType.REFRESH){
                remoteKeysDao.deleteAll()
                upcomingMoviesDao.deleteAll()
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaging) null else page + 1
            val key = items.map {
                UpcomingMoviesRemoteKeys(it.id, prevKey, nextKey)
            }
            moviesDao.insertAll(items)
            remoteKeysDao.insertAllItems(key)
            upcomingMoviesDao.insertAll(items.map { UpcomingMoviesEntity(it.id) })
        }
        return MediatorResult.Success(endOfPaginationReached = endOfPaging)
    }

    private suspend fun apiCall(page: Int): LoadStatus<MoviesResponse> {
        return remoteSource.getUpcomingMovies(language, page)
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): UpcomingMoviesRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                remoteKeysDao.getItemById(item.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): UpcomingMoviesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                remoteKeysDao.getItemById(item.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): UpcomingMoviesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { itemId ->
                remoteKeysDao.getItemById(itemId)
            }
        }
    }
}