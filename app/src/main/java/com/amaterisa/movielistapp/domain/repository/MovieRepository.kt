package com.amaterisa.movielistapp.domain.repository

import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import com.amaterisa.movielistapp.domain.common.Resource

interface MovieRepository {
    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun saveMovieToWatchList(movie: Movie)

    fun getWatchList(): Flow<List<Movie>>

    suspend fun removeMovieFromWatchList(id: Long)

    suspend fun markWatchListMovie(movie: Movie)

    fun getGenreList(): Flow<Resource<List<Genre>>>

    fun getMoviesByGenre(genre: List<Genre>): Flow<Resource<Map<Genre, List<Movie>>>>

    fun getMovieDetails(id: Long): Flow<Resource<Movie?>>

    suspend fun saveMovies(movies: List<Movie>)

    fun getAllGenres(): Flow<List<Genre>>

    fun searchMovie(name: String): Flow<Resource<List<Movie>>>
}