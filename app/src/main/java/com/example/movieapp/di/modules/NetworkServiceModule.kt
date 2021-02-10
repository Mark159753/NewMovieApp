package com.example.movieapp.di.modules

import android.content.Context
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.remote.MovieRemoteService
import com.example.movieapp.data.remote.datasource.RemoteDataSource
import com.example.movieapp.data.remote.interceptors.AuthenticationInterceptor
import com.example.movieapp.until.BASE_API_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkServiceModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideAuthInterceptor():AuthenticationInterceptor{
        return AuthenticationInterceptor()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) logger.level = HttpLoggingInterceptor.Level.BODY
        else logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(
            logging:HttpLoggingInterceptor,
            authInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideMovieService(okHttpClient:OkHttpClient):MovieRemoteService{
        return Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MovieRemoteService::class.java)
    }


    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: MovieRemoteService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

}