package com.example.movieapp.data.remote.datasource.abstraction

import com.example.movieapp.data.model.people.PeopleDetails
import com.example.movieapp.data.remote.LoadStatus

interface PeopleDetailsSource {

    suspend fun getPersonDetails(
            personId:Int,
            language:String = "en-US" // uk-UK Українська
    ): LoadStatus<PeopleDetails>
}