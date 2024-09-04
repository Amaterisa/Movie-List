package com.amaterisa.movielistapp.data.mapper

import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import com.amaterisa.movielistapp.domain.model.Movie

object MovieMapper {
    fun getMovieFromResponse(movieListResponse: MovieListResponse): List<Movie> {
        return movieListResponse.results.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = it.genreIds,
            )
        }
    }
}