package com.amaterisa.movielistapp.domain.repository

import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<List<Movie>>

    fun saveMovieToWatchList(movie: Movie)

    fun getGenreList(): Flow<List<Genre>>

    fun getMoviesByGenre(genre: Genre): Flow<List<Movie>>
}