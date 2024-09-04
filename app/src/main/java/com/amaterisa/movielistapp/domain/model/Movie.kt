package com.amaterisa.movielistapp.domain.model

data class Movie(
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: String,
    val genreIds: List<Int>
)
