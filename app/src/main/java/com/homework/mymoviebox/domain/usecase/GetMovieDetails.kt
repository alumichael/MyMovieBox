package com.homework.mymoviebox.domain.usecase

import com.homework.mymoviebox.domain.repository.Repository

import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(id: Int) = repository.fetchMovie(id)
}