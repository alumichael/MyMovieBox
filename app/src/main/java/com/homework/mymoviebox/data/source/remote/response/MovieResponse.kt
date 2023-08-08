package com.homework.mymoviebox.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.homework.mymoviebox.data.source.remote.response.CreditsResponse
import com.homework.mymoviebox.data.source.remote.response.GenreResponse

data class MovieResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("backdrop_path")
    val backdrop: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("genres")
    val genres: List<GenreResponse>,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Number,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("credits")
    val credits: CreditsResponse,
)