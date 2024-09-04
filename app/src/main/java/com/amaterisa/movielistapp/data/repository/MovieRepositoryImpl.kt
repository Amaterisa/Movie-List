package com.amaterisa.movielistapp.data.repository

import com.amaterisa.movielistapp.data.mapper.MovieMapper
import com.amaterisa.movielistapp.data.source.remote.MovieApiService
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.amaterisa.movielistapp.domain.common.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): MovieRepository {

    override suspend fun getPopularMovies(): Flow<List<Movie>> {
        return flow {
            val result = getPopularMoviesRemote()
            if (result is Result.Success) {
                emit(result.data)
            }
        }
    }

    private suspend fun getPopularMoviesRemote(): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getPopularMovies()
                return@withContext Result.Success(MovieMapper.getMovieFromResponse(response))
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}