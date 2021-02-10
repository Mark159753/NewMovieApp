package com.example.movieapp.ui.home.adapter

import androidx.paging.PagingDataAdapter

data class HomeItem(
        val title:String,
        val adapter: PagingDataAdapter<*, *>
)