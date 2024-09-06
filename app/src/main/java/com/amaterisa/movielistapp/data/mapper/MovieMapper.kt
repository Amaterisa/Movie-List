package com.amaterisa.movielistapp.data.mapper

import com.amaterisa.movielistapp.data.source.local.entity.WatchListMovieEntity
import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.model.WatchListMovie
import com.amaterisa.movielistapp.utils.ListUtils.transformListToString
import com.amaterisa.movielistapp.utils.ListUtils.transformStringToList

object MovieMapper {
    fun getMovieFromResponse(movieListResponse: MovieListResponse): List<Movie> {
        return movieListResponse.results.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = it.genreIds,
            )
        }
    }

    fun getWatchListMovieEntityFromMovie(movie: Movie): WatchListMovieEntity {
        return movie.let {
            WatchListMovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = transformListToString(it.genreIds),
                markWatched = false
            )
        }
    }

    fun getMovieFromEntity(watchListMovieEntity: WatchListMovieEntity): Movie {
        return watchListMovieEntity.let {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = transformStringToList(it.genreIds),
                isInWatchList = true
            )
        }
    }

    fun getWatchListMovieFromEntity(watchListMovieEntity: WatchListMovieEntity): WatchListMovie {
        return watchListMovieEntity.let {
            WatchListMovie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = transformStringToList(it.genreIds),
                markWatched = it.markWatched
            )
        }
    }

    fun getEntityFromWatchListMovie(watchListMovie: WatchListMovie): WatchListMovieEntity {
        return watchListMovie.let {
            WatchListMovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = transformListToString(it.genreIds),
                markWatched = it.markWatched
            )
        }
    }
}