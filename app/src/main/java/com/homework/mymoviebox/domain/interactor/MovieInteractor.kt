package com.homework.mymoviebox.domain.interactor

import com.homework.mymoviebox.domain.usecase.FetchMovies
import com.homework.mymoviebox.domain.usecase.SearchMovie
import javax.inject.Inject

data class MovieInteractor @Inject constructor(
    override val fetchMovies: FetchMovies,
    override val searchMovie: SearchMovie,
) : MovieUseCase

interface MovieUseCase {
    val fetchMovies: FetchMovies
    val searchMovie: SearchMovie
}