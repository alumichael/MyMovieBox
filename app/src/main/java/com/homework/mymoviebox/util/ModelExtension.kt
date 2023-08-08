package com.homework.mymoviebox.util

import com.homework.mymoviebox.data.source.remote.response.GenreResponse


fun mapGenre(genre: GenreResponse): String = genre.name
