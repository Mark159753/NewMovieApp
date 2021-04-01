package com.example.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.search.SearchItem
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.SearchDataSource
import com.example.movieapp.data.remote.datasource.abstraction.SimilarMoviesSource

class SearchPagingDataSource (
        private val remoteSource: SearchDataSource,
        private val query:String,
        private val language:String
        ): PagingSource<Int, SearchItem>() {

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        try {
            val key = params.key ?: 1
            val res = remoteSource.searchQuery(query, key, language)
            return when(res){
                is LoadStatus.Success -> {
                    LoadResult.Page(
                            data = res.data.results!!,
                            prevKey = if (key == 1) null else key - 1,
                            nextKey = if (res.data.results.isEmpty()) null else key + 1
                    )
                }
                is LoadStatus.Error -> {
                    LoadResult.Error(res.e ?: UnknownError(res.msg))
                }
            }
        }catch (e:Exception){
                return LoadResult.Error(e)
        }
    }
}