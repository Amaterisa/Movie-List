package com.amaterisa.movielistapp.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: String,
    val genreIds: List<Int>,
    var isInWatchList: Boolean = false,
    var markWatched: Boolean = false
)
