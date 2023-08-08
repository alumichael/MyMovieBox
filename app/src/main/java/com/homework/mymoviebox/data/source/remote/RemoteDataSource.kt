package com.homework.mymoviebox.data.source.remote

import com.homework.mymoviebox.data.source.remote.network.MyMovieBoxServices
import com.homework.mymoviebox.data.source.remote.response.MovieResponse
import com.homework.mymoviebox.data.source.remote.response.ResponseFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val retrofit: MyMovieBoxServices,
) {
    suspend fun getMovies(page: Int) = retrofit.getMovies(page)

    suspend fun searchMovie(query: String, page: Int) = retrofit.searchMovie(query, page)

    fun getMovie(id: Int) = object : ResponseFlow<MovieResponse>() {
        override suspend fun responseCall(): MovieResponse =
            retrofit.getMovie(id)
    }.flow

}