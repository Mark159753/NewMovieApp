package com.example.movieapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.data.local.dao.PopularTvShowDao
import com.example.movieapp.data.local.dao.TvShowDao
import com.example.movieapp.data.local.entitys.PopularTvShowsEntity
import com.example.movieapp.data.local.entitys.TvShow
import com.example.movieapp.data.local.entitys.remoteKeys.PopularTvShowRemoteKeys
import com.example.movieapp.data.model.tvShowResponse.TvShowResponse
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.PopularTvShowSource
import com.example.movieapp.until.UNKNOWN_ERROR
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class PopularTvShowMediator(
        private val db: MovieDb,
        private val remoteSource: PopularTvShowSource,
        private val popularTvShowsDao: PopularTvShowDao,
        private val tvShowDao:TvShowDao,
        private val language:String
): RemoteMediator<Int, TvShow>() {

    private val remoteKeysDao = db.getPopularTvShowsRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TvShow>): MediatorResult {
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

    private suspend fun saveData(apiResponse: TvShowResponse, loadType: LoadType, page: Int): MediatorResult {
        val items = apiResponse.results
        val endOfPaging = items!!.isEmpty()

        db.withTransaction {
            if (loadType == LoadType.REFRESH){
                remoteKeysDao.deleteAll()
                popularTvShowsDao.deleteAll()
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaging) null else page + 1
            val key = items.map {
                PopularTvShowRemoteKeys(it.id, prevKey, nextKey)
            }
            remoteKeysDao.insertAllItems(key)

            tvShowDao.insertAll(items)
            popularTvShowsDao.insertAll(items.map { PopularTvShowsEntity(it.id) })
        }
        return MediatorResult.Success(endOfPaginationReached = endOfPaging)
    }

    private suspend fun apiCall(page: Int): LoadStatus<TvShowResponse> {
        return remoteSource.getPopularTvShows(language, page)
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TvShow>): PopularTvShowRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { item ->
                    remoteKeysDao.getItemById(item.id)
                }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TvShow>): PopularTvShowRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { item ->
                    remoteKeysDao.getItemById(item.id)
                }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TvShow>): PopularTvShowRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { itemId ->
                remoteKeysDao.getItemById(itemId)
            }
        }
    }
}