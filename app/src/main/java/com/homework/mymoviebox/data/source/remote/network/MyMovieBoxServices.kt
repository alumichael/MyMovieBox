package com.homework.mymoviebox.data.source.remote.network

import com.homework.mymoviebox.data.Constants
import com.homework.mymoviebox.data.source.remote.response.ListResponse
import com.homework.mymoviebox.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyMovieBoxServices {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1,
    ): ListResponse<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("page")
        page: Int = 1,
    ): ListResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        id: Int,
        @Query("append_to_response")
        extra: String = Constants.appendToResponse,
    ): MovieResponse

}