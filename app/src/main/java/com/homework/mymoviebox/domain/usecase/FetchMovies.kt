package com.homework.mymoviebox.domain.usecase

import com.homework.mymoviebox.domain.repository.Repository
import javax.inject.Inject

class FetchMovies @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.fetchMovies()
}