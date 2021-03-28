package com.example.movieapp.ui.details.person.adapter

interface PeopleItemClickListener {

    fun onItemClicked(type:ItemType, id:Int)

    enum class ItemType{
        Movie,
        Tv
    }
}