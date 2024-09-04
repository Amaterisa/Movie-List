package com.amaterisa.movielistapp.domain.usecase

import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.repository.MovieRepository

class GetMoviesByGenreUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(genre: Genre) = movieRepository.getMoviesByGenre(genre)
}