package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.domain.model.TrendsData
import kotlinx.coroutines.flow.Flow

interface TrendRepository {

    fun getTrends(mediaType:String, timeWindow:String, language:String): Flow<PagingData<TrendsData>>
}