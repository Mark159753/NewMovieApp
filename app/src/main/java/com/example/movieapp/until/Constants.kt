package com.example.movieapp.until

const val BASE_API_URL = "https://api.themoviedb.org/3/"
const val UNKNOWN_ERROR = "Unknown Error"
const val NO_INTERNET_EXCEPTION = "No Internet Connection"
const val EMPTY_RESULT = "Empty result"

enum class MediaTypes(val type:String){
    All("all"),
    Movie("movie"),
    Tv("tv"),
    Person("person")
}

enum class TimeWindow(val timeWindow: String){
    Day("day"),
    Week("week")
}