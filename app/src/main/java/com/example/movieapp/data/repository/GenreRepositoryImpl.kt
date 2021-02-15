package com.example.movieapp.data.repository

import com.example.movieapp.data.local.dao.GenreDao
import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.data.local.entitys.genre.TvGenre
import com.example.movieapp.data.model.genre.Genre
import com.example.movieapp.data.remote.LoadStatus
import com.example.movieapp.data.remote.datasource.abstraction.MovieGenreSource
import com.example.movieapp.data.remote.datasource.abstraction.TvGenreSource
import com.example.movieapp.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
        private val genreDao: GenreDao,
        private val movieGenreSource: MovieGenreSource,
        private val tvGenreSource: TvGenreSource
):GenreRepository {

    override suspend fun getMovieGenre(language: String): List<MovieGenre>? {
        val res = genreDao.getAllMovieGenre()
        if (res != null && res.isNotEmpty()){
            if (res[0].language == language) {
                return res
            }
            else{
                genreDao.deleteAllMovieGenre()
            }
        }

        when(val response = movieGenreSource.getGenreMovieList(language)){
            is LoadStatus.Success -> {
                genreDao.insertAllMovieGenre(response.data.genres!!.map { Genre.mapToMovieGenre(it, language) })
            }
            is LoadStatus.Error -> {
                return null
            }
        }
        return genreDao.getAllMovieGenre()
    }

    override suspend fun getTvGenre(language: String): List<TvGenre>? {
        val res = genreDao.getAllTvGenre()
        if (res != null && res.isNotEmpty()) {
            if (res[0].language == language) {
                return res
            }
            else{
                genreDao.deleteAllFromTvGenre()
            }
        }

        when(val response = tvGenreSource.getGenreTvList(language)){
            is LoadStatus.Success -> {
                genreDao.insertAllTvGenre(response.data.genres!!.map { Genre.mapToTvGenre(it, language) })
            }
            is LoadStatus.Error -> {
                return null
            }
        }
        return genreDao.getAllTvGenre()
    }
}