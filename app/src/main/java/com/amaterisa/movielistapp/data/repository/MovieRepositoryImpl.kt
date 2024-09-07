package com.amaterisa.movielistapp.data.repository

import android.util.Log
import com.amaterisa.movielistapp.data.mapper.GenreMapper
import com.amaterisa.movielistapp.data.mapper.MovieMapper
import com.amaterisa.movielistapp.data.source.local.dao.GenreDao
import com.amaterisa.movielistapp.data.source.local.dao.MovieDao
import com.amaterisa.movielistapp.data.source.remote.MovieApiService
import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import com.amaterisa.movielistapp.domain.common.Result
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
) : MovieRepository {

    companion object {
        const val TAG = "MovieRepository"
    }

    override fun getPopularMovies(): Flow<List<Movie>> {
        return flow {
            val result = getPopularMoviesRemote()
            if (result is Result.Success) {
                saveMovies(result.data)
                emit(result.data)
            } else {
                val entity = movieDao.getPopularMovies()
                val movies = entity?.map { MovieMapper.getMovieFromEntity(it) } ?: emptyList()
                emit(movies)
            }
        }
    }

    override suspend fun saveMovieToWatchList(movie: Movie) {
        withContext(Dispatchers.IO) {
            movie.isInWatchList = true
            val entity = movieDao.getMovie(movie.id)
            if (entity.first() != null) {
                movieDao.updateMovieWatchList(movie.id, movie.isInWatchList)
            } else {
                movieDao.insert(MovieMapper.getMovieEntityFromMovie(movie))
            }
        }
    }

    override fun getWatchList(): Flow<List<Movie>> {
        return movieDao.getWatchListMovies().map { movies ->
            movies?.map { movie ->
                MovieMapper.getMovieFromEntity(movie)
            } ?: emptyList<Movie>()
        }
    }

    override suspend fun removeMovieFromWatchList(id: Long) {
        withContext(Dispatchers.IO) {
            movieDao.updateMovieWatchList(id, false)
        }
    }

    override suspend fun markWatchListMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movie.markWatched = !movie.markWatched
            movieDao.updateMarkedStatus(movie.id, movie.markWatched)
        }
    }

    override fun getGenreList(): Flow<List<Genre>> {
        return flow {
            val genreEntities = genreDao.getAll()
            if (genreEntities.isNotEmpty()) {
                emit(GenreMapper.getGenreListFromEntity(genreEntities))
            } else {
                val result = getGenreListRemote()
                if (result is Result.Success) {
                    saveGenres(result.data)
                    emit(result.data)
                }
            }
        }
    }

    override fun getMoviesByGenre(genre: Genre): Flow<List<Movie>> {
        return flow {
            val result = getMoviesByGenreRemote(genre)
            if (result is Result.Success) {
                saveMovies(result.data)
                emit(result.data)
            }
        }
    }

    override fun getMovieDetails(id: Long): Flow<Movie?> {
        val movies = movieDao.getMovie(id).map {
            if (it == null) {
                val result = getMoviesDetailsRemote(id)
                if (result is Result.Success) {
                    movieDao.insert(MovieMapper.getMovieEntityFromMovie(result.data))
                    return@map result.data
                } else {
                    return@map null
                }
            } else {
                return@map MovieMapper.getMovieFromEntity(it)
            }
        }
        return movies
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) {
            val entities = movies.map { MovieMapper.getMovieEntityFromMovie(it) }
            movieDao.insertAll(entities)
        }
    }

    override fun getAllGenres(): Flow<List<Genre>> {
        return flow {
            emit(GenreMapper.getGenreListFromEntity(genreDao.getAll()))
        }
    }

    override fun searchMovie(name: String): Flow<List<Movie>> {
        return flow {
            try {
                val response = movieApiService.searchMovie(name)
                val movies =
                    response.results.filter { it.posterPath != null && it.backdropPath != null && it.overview != null }
                emit(MovieMapper.getMovieFromResponse(MovieListResponse(response.page, movies)))
            } catch (e: Exception) {
                Log.d(TAG, "error $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getPopularMoviesRemote(): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getPopularMovies()
                return@withContext Result.Success(MovieMapper.getMovieFromResponse(response, true))
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

    private suspend fun getMoviesDetailsRemote(id: Long): Result<Movie> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getMoviesDetails(id)
                return@withContext Result.Success(
                    MovieMapper.getMovieFromMovieDetailsResponse(
                        response
                    )
                )
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    private suspend fun saveGenres(genres: List<Genre>) {
        genreDao.insertAll(GenreMapper.getGenreEntityListFromGenre(genres))
    }
}