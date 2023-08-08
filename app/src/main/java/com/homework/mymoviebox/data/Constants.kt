package com.homework.mymoviebox.data


object Constants {
    // Network
    const val appendToResponse = "keywords,credits"
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
    private val posterSizes = arrayOf(
        "w185",
        "w342",
        "w780",
        "original"
    )
    private val backdropSizes = arrayOf(
        "w300",
        "w780",
        "w1280",
        "original"
    )

    fun getPosterUrl(path: String) =
        "$IMAGE_BASE_URL/${"w342"}/$path"

    fun getBackdropUrl(path: String) =
        "$IMAGE_BASE_URL/${"w342"}/$path"

    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_PAGE_INDEX = 1

    // Local
    const val DATABASE_NAME = "mymoviebox.db"
}