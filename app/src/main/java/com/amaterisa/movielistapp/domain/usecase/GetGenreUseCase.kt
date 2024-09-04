package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.repository.MovieRepository

class GetGenreUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke() = movieRepository.getGenreList()
}