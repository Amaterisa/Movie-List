package com.amaterisa.movielistapp.domain.repository

import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<List<Movie>>

    suspend fun saveMovieToWatchList(movie: Movie)

    fun getWatchList(): Flow<List<Movie>>

    suspend fun removeMovieFromWatchList(id: Long)

    suspend fun markWatchListMovie(movie: Movie)

    fun getGenreList(): Flow<List<Genre>>

    fun getMoviesByGenre(genre: Genre): Flow<List<Movie>>

    fun getMovieDetails(id: Long): Flow<Movie?>

    suspend fun saveMovies(movies: List<Movie>)

    fun getAllGenres(): Flow<List<Genre>>
}