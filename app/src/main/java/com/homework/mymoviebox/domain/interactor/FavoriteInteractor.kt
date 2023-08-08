package com.homework.mymoviebox.domain.interactor


import com.homework.mymoviebox.domain.usecase.GetFavoriteMovies
import javax.inject.Inject

data class FavoriteInteractor @Inject constructor(
    override val getFavoriteMovies: GetFavoriteMovies
) : FavoriteUseCase

interface FavoriteUseCase {
    val getFavoriteMovies: GetFavoriteMovies
}