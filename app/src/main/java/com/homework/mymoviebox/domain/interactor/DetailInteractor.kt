package com.homework.mymoviebox.domain.interactor

import com.homework.mymoviebox.domain.usecase.GetMovieDetails
import com.homework.mymoviebox.domain.usecase.SetFavoriteMovie
import javax.inject.Inject


data class DetailInteractor @Inject constructor(
    override val getMovieDetails: GetMovieDetails,
    override val setFavoriteMovie: SetFavoriteMovie
) : DetailUseCase

interface DetailUseCase {
    val getMovieDetails: GetMovieDetails
    val setFavoriteMovie: SetFavoriteMovie
}