package com.homework.mymoviebox.domain.usecase


import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.domain.repository.Repository
import javax.inject.Inject

class SetFavoriteMovie @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(movie: Movie, isFavorite: Boolean) = repository.setFavorite(movie, isFavorite)
}