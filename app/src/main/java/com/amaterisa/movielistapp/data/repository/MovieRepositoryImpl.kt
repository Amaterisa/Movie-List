package com.amaterisa.movielistapp.data.repository

import com.amaterisa.movielistapp.data.mapper.GenreMapper
import com.amaterisa.movielistapp.data.mapper.MovieMapper
import com.amaterisa.movielistapp.data.source.local.dao.WatchListMovieDao
import com.amaterisa.movielistapp.data.source.remote.MovieApiService
import com.amaterisa.movielistapp.domain.common.Result
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.model.WatchListMovie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val watchListMovieDao: WatchListMovieDao
) : MovieRepository {

    override fun getPopularMovies(): Flow<List<Movie>> {
        return flow {
            val result = getPopularMoviesRemote()
            if (result is Result.Success) {
                emit(result.data)
            }
        }
    }

    override suspend fun saveMovieToWatchList(movie: Movie) {
        val entity = MovieMapper.getWatchListMovieEntityFromMovie(movie)
        watchListMovieDao.insert(entity)
    }

    override suspend fun getWatchList(): List<WatchListMovie> {
        val entities = watchListMovieDao.getAllMovies()
        return entities.map { MovieMapper.getWatchListMovieFromEntity(it) }
    }

    override suspend fun removeMovieFromWatchList(id: Long) {
        watchListMovieDao.deleteById(id)
    }

    override suspend fun markWatchListMovie(movie: WatchListMovie) {
        movie.markWatched = !movie.markWatched
        watchListMovieDao.updateMarkedStatus(movie.id, movie.markWatched)
    }

    override fun getGenreList(): Flow<List<Genre>> {
        return flow {
            val result = getGenreListRemote()
            if (result is Result.Success) {
                emit(result.data)
            }
        }
    }

    override fun getMoviesByGenre(genre: Genre): Flow<List<Movie>> {
        return flow {
            val result = getMoviesByGenreRemote(genre)
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

    private suspend fun getGenreListRemote(): Result<List<Genre>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getGenres()
                return@withContext Result.Success(GenreMapper.getGenreListFromResponse(response))
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    private suspend fun getMoviesByGenreRemote(genre: Genre): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getMoviesByGenre(genre.id)
                return@withContext Result.Success(MovieMapper.getMovieFromResponse(response))
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}