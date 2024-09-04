package com.amaterisa.movielistapp.domain.repository

import com.amaterisa.movielistapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<List<Movie>>
}