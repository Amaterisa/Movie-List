package com.amaterisa.movielistapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_watch_list")
data class WatchListMovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: String,
    val genreIds: String,
    var markWatched: Boolean
)