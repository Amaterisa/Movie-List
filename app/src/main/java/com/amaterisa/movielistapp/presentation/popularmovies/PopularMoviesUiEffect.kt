package com.amaterisa.movielistapp.presentation.popularmovies

import com.amaterisa.movielistapp.domain.model.Movie

sealed class PopularMoviesUiEffect {
    data class NavigateToMovieDetails(val movie: Movie) : PopularMoviesUiEffect()
}