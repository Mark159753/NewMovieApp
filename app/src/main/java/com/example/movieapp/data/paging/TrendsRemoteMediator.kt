package com.example.movieapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.data.local.dao.TrendsDao
import com.example.movieapp.data.local.entitys.Trends
import com.example.movieapp.data.local.entitys.remoteKeys.TrendRemoteKeys
import com.example.movieapp.data.model.trending.TrendsResponse
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.MovieTrendsSource
import com.example.movieapp.until.UNKNOWN_ERROR
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class TrendsRemoteMediator(
        private val db: MovieDb,
        private val trendsDao: TrendsDao,
        private val remoteSource: MovieTrendsSource,
        private val mediaType:String,
        private val timeWindow:String,
        private val language:String
): RemoteMediator<Int, Trends>() {

    private val remoteKeysDao = db.getTrendsRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Trends>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: throw InvalidObjectException("Remote key and the prevKey should not be null")
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
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

    private suspend fun saveData(apiResponse:TrendsResponse, loadType:LoadType, page: Int): MediatorResult {
        val items = apiResponse.results
        val endOfPaging = items!!.isEmpty()

        db.withTransaction {
            if (loadType == LoadType.REFRESH){
                remoteKeysDao.deleteAll()
                trendsDao.deleteAllItems()
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaging) null else page + 1
            val key = items.map {
                TrendRemoteKeys(it.id, prevKey, nextKey)
            }
            remoteKeysDao.insertAllItems(key)
            trendsDao.insertAllItems(items)
        }

        return MediatorResult.Success(endOfPaginationReached = endOfPaging)
    }

    private suspend fun apiCall(page: Int): LoadStatus<TrendsResponse> {
        return remoteSource.getMovieTrendsSource(
            media_type = mediaType,
            time_window = timeWindow,
            language = language,
            page = page
        )
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Trends>): TrendRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { item ->
                    remoteKeysDao.getItemById(item.id)
                }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Trends>): TrendRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { item ->
                    remoteKeysDao.getItemById(item.id)
                }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Trends>): TrendRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { itemId ->
                remoteKeysDao.getItemById(itemId)
            }
        }
    }
}