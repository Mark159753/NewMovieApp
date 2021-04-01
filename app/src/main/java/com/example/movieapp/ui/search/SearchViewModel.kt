package com.example.movieapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.model.search.SearchItem
import com.example.movieapp.data.paging.SearchPagingDataSource
import com.example.movieapp.data.remote.datasource.abstraction.SearchDataSource
import com.example.movieapp.until.LocaleHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val remoteSource: SearchDataSource,
        private val context: Context
): ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult:Flow<PagingData<SearchItem>>? = null

    fun searchQuery(query:String): Flow<PagingData<SearchItem>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null){
            return lastResult
        }
        currentQueryValue = query
        val newResult = Pager(
                config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { SearchPagingDataSource(remoteSource, query, LocaleHelper.getLanguage(context)) }
        ).flow
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}