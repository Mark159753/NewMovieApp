package com.example.movieapp.data.remote.interceptors

import com.example.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldUrl = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
        val newUrl = chain.request()
                .newBuilder()
                .url(oldUrl)
                .build()
        return chain.proceed(newUrl)
    }
}