package com.example.movieapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.data.local.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context):MovieDb{
        return Room.databaseBuilder(context, MovieDb::class.java, "movieDB")
                .fallbackToDestructiveMigration()
                .build()
    }


    @Provides
    fun provideTrendsDao(db:MovieDb):TrendsDao{
        return db.getTrendsDao()
    }

    @Provides
    fun provideMovieDao(db: MovieDb):MovieDao{
        return db.getMovieDao()
    }

    @Provides
    fun provideNowStreamingMoviesDao(db: MovieDb):NowStreamingMoviesDao{
        return db.getNowStreamingDao()
    }

    @Provides
    fun providePopularTvShowDao(db: MovieDb):PopularTvShowDao{
        return db.getPopularTvShowDao()
    }

    @Provides
    fun provideTvShowDao(db: MovieDb):TvShowDao{
        return db.getTvShowDao()
    }

    @Provides
    fun providePopularMovieDao(db: MovieDb):PopularMovieDao{
        return db.getPopularMovieDao()
    }
}