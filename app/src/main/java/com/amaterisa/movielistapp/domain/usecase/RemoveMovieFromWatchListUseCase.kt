package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.repository.MovieRepository

class RemoveMovieFromWatchListUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(id: Long) = movieRepository.removeMovieFromWatchList(id)
}