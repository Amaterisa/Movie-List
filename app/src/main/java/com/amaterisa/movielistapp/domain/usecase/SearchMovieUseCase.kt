package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.repository.MovieRepository

class SearchMovieUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(name: String) = movieRepository.searchMovie(name)
}