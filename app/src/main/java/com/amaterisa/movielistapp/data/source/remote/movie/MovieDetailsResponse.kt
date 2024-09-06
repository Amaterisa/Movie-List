package com.amaterisa.movielistapp.data.source.remote.movie

import com.amaterisa.movielistapp.data.source.remote.genre.GenreResponse
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: String,
    @SerializedName("genres") val genres: List<GenreResponse>
)
