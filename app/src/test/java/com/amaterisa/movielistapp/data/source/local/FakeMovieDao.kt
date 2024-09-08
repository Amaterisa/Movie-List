package com.amaterisa.movielistapp.data.source.local

import com.amaterisa.movielistapp.data.source.local.dao.MovieDao
import com.amaterisa.movielistapp.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieDao : MovieDao {
    val movieList: MutableList<MovieEntity> = mutableListOf()

    override suspend fun insert(movie: MovieEntity) {
        movieList.add(movie)
    }

    override suspend fun insertAll(movies: List<MovieEntity>) {
        movieList.addAll(movies)
    }

    override fun getMovie(id: Long): Flow<MovieEntity?> {
        val movie = movieList.find { id == it.id }
        return flowOf(movie)
    }

    override fun getWatchListMovies(): Flow<List<MovieEntity>?> {
        val watchList = movieList.filter { it.isInWatchList }
        return flowOf(watchList)
    }

    override suspend fun updateMovieWatchList(id: Long, marked: Boolean) {
        val movie = movieList.find { id == it.id }
        movie?.let {
            movieList.remove(movie)
            it.isInWatchList = marked
            movieList.add(movie)
        }
    }

    override suspend fun updateMarkedStatus(id: Long, marked: Boolean) {
        val movie = movieList.find { id == it.id }
        movie?.let {
            movieList.remove(movie)
            it.markWatched = marked
            movieList.add(movie)
        }
    }
}