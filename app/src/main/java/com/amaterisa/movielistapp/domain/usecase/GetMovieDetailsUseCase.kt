package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.repository.MovieRepository

class GetMovieDetailsUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(id: Long) = movieRepository.getMovieDetails(id)
}