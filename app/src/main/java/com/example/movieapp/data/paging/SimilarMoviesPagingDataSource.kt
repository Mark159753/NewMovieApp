package com.example.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.SimilarMoviesSource

class SimilarMoviesPagingDataSource(
        private val remoteSource:SimilarMoviesSource,
        private val movieId:Int,
        private val language:String
):PagingSource<Int, SimilarMovie>() {

    override fun getRefreshKey(state: PagingState<Int, SimilarMovie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimilarMovie> {
        try {
            val key = params.key ?: 1
            val res = remoteSource.getSimilarMovies(movieId, language)
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