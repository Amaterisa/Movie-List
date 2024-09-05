package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.model.WatchListMovie
import com.amaterisa.movielistapp.domain.repository.MovieRepository

class MarkWatchListMovieUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movie: WatchListMovie) = movieRepository.markWatchListMovie(movie)
}