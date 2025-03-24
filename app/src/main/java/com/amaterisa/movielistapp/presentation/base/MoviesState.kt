package com.amaterisa.movielistapp.presentation.base

import com.amaterisa.movielistapp.domain.model.Movie

sealed class MoviesState(open val uiModel: MovieListUiModel) {
    data object Initial : MoviesState(MovieListUiModel())

    data class Loading(override val uiModel: MovieListUiModel) :
        MoviesState(MovieListUiModel())

    data class Resume(override val uiModel: MovieListUiModel) :
        MoviesState(MovieListUiModel())

    data class Error(override val uiModel: MovieListUiModel) :
        MoviesState(MovieListUiModel())
}

data class MovieListUiModel(
    val movies: List<Movie> = emptyList()
)