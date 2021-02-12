package com.example.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.local.entitys.Trends
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.MovieTrendsSource

class TrendPagingDataSource(
        private val remoteSource: MovieTrendsSource,
        private val mediaType:String,
        private val timeWindow:String,
        private val language:String
):PagingSource<Int, Trends>() {

    override fun getRefreshKey(state: PagingState<Int, Trends>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Trends> {
        try {
            val key = params.key ?: 1
            val response = remoteSource.getMovieTrendsSource(mediaType, timeWindow, language, key)
            return when(response){
                is LoadStatus.Success -> {
                    LoadResult.Page(
                            data = response.data.results!!,
                            prevKey = if (key == 1) null else key - 1,
                            nextKey = key + 1
                    )
                }
                is LoadStatus.Error -> {
                    LoadResult.Error(response.e ?: UnknownError(response.msg))
                }
            }
        }catch (e:Exception){
            return LoadResult.Error(e)
        }
    }
}