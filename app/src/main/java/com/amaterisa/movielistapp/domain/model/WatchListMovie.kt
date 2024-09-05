package com.amaterisa.movielistapp.domain.model

data class WatchListMovie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: String,
    val genreIds: List<Int>,
    var markWatched: Boolean = false
)