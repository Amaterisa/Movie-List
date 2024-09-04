package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository

class SaveMovieToWatchListUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(movie: Movie) = movieRepository.saveMovieToWatchList(movie)
}