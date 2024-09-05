package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.repository.MovieRepository

class GetWatchListUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke() = movieRepository.getWatchList()
}