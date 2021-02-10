package com.example.movieapp.data.remote


import com.example.movieapp.until.EMPTY_RESULT
import com.example.movieapp.until.UNKNOWN_ERROR
import retrofit2.Response

sealed class LoadStatus<out T> {
    data class Success<T>(val data:T): LoadStatus<T>()
    data class Error(val msg:String?, val e:Throwable?): LoadStatus<Nothing>()
}

suspend fun <T> saveApiCall(api:suspend () -> Response<T>):LoadStatus<T>{
    return try {
        val response = api.invoke()
        if (response.isSuccessful){
            val body = response.body()
            if (body == null || response.code() == 204)
                LoadStatus.Error(EMPTY_RESULT, null)
            else
                LoadStatus.Success<T>(body)
        }else{
            LoadStatus.Error(UNKNOWN_ERROR, null)
        }
    }catch (e:Exception){
        LoadStatus.Error(UNKNOWN_ERROR, e)
    }
}