package com.homework.mymoviebox.presentation.home.movie

sealed class MovieIntent {
    object FetchMovie : MovieIntent()
    object FetchFavouriteMovie : MovieIntent()
    object SearchMovie : MovieIntent()
}