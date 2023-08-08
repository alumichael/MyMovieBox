package com.homework.mymoviebox.domain.usecase


import com.homework.mymoviebox.domain.repository.Repository
import javax.inject.Inject

class SearchMovie @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(query: String) = repository.searchMovie(query)
}