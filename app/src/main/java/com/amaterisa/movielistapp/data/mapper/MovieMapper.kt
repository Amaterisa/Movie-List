package com.amaterisa.movielistapp.data.mapper

import com.amaterisa.movielistapp.data.source.local.entity.MovieEntity
import com.amaterisa.movielistapp.data.source.remote.movie.MovieDetailsResponse
import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.utils.ListUtils.transformListToString
import com.amaterisa.movielistapp.utils.ListUtils.transformStringToList

object MovieMapper {
    fun getMovieFromResponse(movieListResponse: MovieListResponse, isTrending: Boolean = false): List<Movie> {
        return movieListResponse.results.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview ?: "",
                posterPath = it.posterPath ?: "",
                backdropPath = it.backdropPath ?: it.posterPath ?: "",
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = it.genreIds,
                isTrending = isTrending
            )
        }
    }

    fun getMovieEntityFromMovie(movie: Movie): MovieEntity {
        return movie.let {
            MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = transformListToString(it.genreIds),
                isInWatchList = it.isInWatchList,
                markWatched = it.markWatched,
                isTrending = it.isTrending
            )
        }
    }

    fun getMovieFromEntity(movieEntity: MovieEntity): Movie {
        return movieEntity.let {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = transformStringToList(it.genreIds),
                isInWatchList = it.isInWatchList,
                markWatched = it.markWatched,
                isTrending = it.isTrending
            )
        }
    }

    fun getMovieFromMovieDetailsResponse(movieDetailsResponse: MovieDetailsResponse): Movie {
        val genreIds = movieDetailsResponse.genres.map { it.id }
        return movieDetailsResponse.let {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview ?: "",
                posterPath = it.posterPath ?: "",
                backdropPath = it.backdropPath ?: it.posterPath ?: "",
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                genreIds = genreIds
            )
        }
    }
}