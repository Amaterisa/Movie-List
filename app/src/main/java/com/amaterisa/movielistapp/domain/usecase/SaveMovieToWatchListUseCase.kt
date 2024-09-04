package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import javax.inject.Inject

class SaveMovieToWatchListUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(movie: Movie) = movieRepository.saveMovieToWatchList(movie)
}