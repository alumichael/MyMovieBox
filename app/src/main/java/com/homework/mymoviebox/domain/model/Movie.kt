package com.homework.mymoviebox.domain.model

import com.homework.mymoviebox.data.Constants

data class Movie(
    val movieId: Int,
    val title: String,
    val backdrop: String,
    val poster: String,
    val genres: String,
    val overview: String,
    val voteAverage: Double,
    val runtime: Int?,
    val releaseDate: String,
    val director: String,
) : DomainModel(movieId) {
    fun getPosterUrl() = Constants.getPosterUrl(poster)
    fun getBackdropUrl() = Constants.getBackdropUrl(backdrop)
}
