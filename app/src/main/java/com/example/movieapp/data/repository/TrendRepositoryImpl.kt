package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapp.data.paging.TrendPagingDataSource
import com.example.movieapp.data.remote.datasource.abstraction.MovieTrendsSource
import com.example.movieapp.domain.mappers.TrendsMapper
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.domain.repository.TrendRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrendRepositoryImpl @Inject constructor(
        private val trendSource:MovieTrendsSource
):TrendRepository {

    override fun getTrends(mediaType: String, timeWindow: String, language: String): Flow<PagingData<TrendsData>> {
        val mapper = TrendsMapper()
        return Pager(
                PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { TrendPagingDataSource(trendSource, mediaType, timeWindow, language) }
        ).flow.map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }
}