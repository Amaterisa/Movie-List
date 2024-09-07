package com.amaterisa.movielistapp.data.repository

import com.amaterisa.movielistapp.data.mapper.GenreMapper
import com.amaterisa.movielistapp.data.mapper.MovieMapper
import com.amaterisa.movielistapp.data.source.local.dao.GenreDao
import com.amaterisa.movielistapp.data.source.local.dao.MovieDao
import com.amaterisa.movielistapp.data.source.remote.MovieApiService
import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
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

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading)
            val result = getPopularMoviesRemote()
            emit(result)
        }.flowOn(Dispatchers.IO)
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
        }.flowOn(Dispatchers.IO)
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

    override fun getGenreList(): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading)
            val genreEntities = genreDao.getAll()
            if (genreEntities.isNotEmpty()) {
                val genres = GenreMapper.getGenreListFromEntity(genreEntities)
                emit(Resource.Success(genres.sortedBy { it.name }))
            } else {
                val result = getGenreListRemote()
                if (result is Resource.Success) {
                    saveGenres(result.data.sortedBy { it.name })
                }
                emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMoviesByGenre(genres: List<Genre>): Flow<Resource<Map<Genre, List<Movie>>>> {
        return flow {
            emit(Resource.Loading)

            val genreFlow = genres.asFlow().map { genre ->
                val result = getMoviesByGenreRemote(genre)
                genre to result
            }.toList()

            val genreMovieMap = genreFlow.associate { (genre, resource) ->
                genre to (if (resource is Resource.Success) resource.data else emptyList())
            }
            emit(Resource.Success(genreMovieMap))
        }.flowOn(Dispatchers.IO)
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
        }.flowOn(Dispatchers.IO)
    }

    override fun searchMovie(name: String): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                val response = movieApiService.searchMovie(name)
                val movies =
                    response.results.filter {
                        it.posterPath != null && it.backdropPath != null && it.overview != null
                    }
                emit(
                    Resource.Success(
                        MovieMapper.getMovieFromResponse(
                            MovieListResponse(
                                response.page,
                                movies
                            )
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getPopularMoviesRemote(): Resource<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getPopularMovies()
                return@withContext Resource.Success(MovieMapper.getMovieFromResponse(response))
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }

    private suspend fun getGenreListRemote(): Resource<List<Genre>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getGenres()
                return@withContext Resource.Success(GenreMapper.getGenreListFromResponse(response))
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }

    private suspend fun getMoviesByGenreRemote(genre: Genre): Resource<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getMoviesByGenre(genre.id)
                return@withContext Resource.Success(MovieMapper.getMovieFromResponse(response))
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }

    private suspend fun saveGenres(genres: List<Genre>) {
        genreDao.insertAll(GenreMapper.getGenreEntityListFromGenre(genres))
    }
}