package com.homework.mymoviebox.data.source.local

import com.homework.mymoviebox.data.mapper.mapToDomain
import com.homework.mymoviebox.data.source.local.dao.MyMovieBoxDao
import com.homework.mymoviebox.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val myMovieBoxDao: MyMovieBoxDao,
) {
    fun getFavoriteMovies() = myMovieBoxDao.getFavoriteMovies()

    fun getFavoriteMovie(id: Int) =
        myMovieBoxDao.getFavoriteMovie(id)
            .map { it?.mapToDomain() }

    suspend fun addToFavorite(movie: MovieEntity) = myMovieBoxDao.addToFavorite(movie)

    suspend fun removeFromFavorite(movie: MovieEntity) = myMovieBoxDao.removeFromFavorite(movie)
}