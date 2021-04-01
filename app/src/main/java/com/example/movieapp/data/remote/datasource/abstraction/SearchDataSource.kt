package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.search.SearchResponse
import com.example.movieapp.data.remote.LoadStatus

interface SearchDataSource {

    suspend fun searchQuery(
        query:String,
        page:Int = 1,
        language:String = "en-US"
    ): LoadStatus<SearchResponse>

}