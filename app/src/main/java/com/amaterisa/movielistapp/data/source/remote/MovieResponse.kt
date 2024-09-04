package com.amaterisa.movielistapp.data.source.remote

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: String,
    @SerializedName("genre_ids") val genreIds: List<Int>

)
