package com.amaterisa.movielistapp.presentation.moviedetails

import com.amaterisa.movielistapp.domain.model.Movie

sealed class MovieDetailsState(open val uiModel: MovieDetailsUiModel = MovieDetailsUiModel()) {
    data class Resume(override val uiModel: MovieDetailsUiModel) :
        MovieDetailsState(uiModel)
}

data class MovieDetailsUiModel(
    val movie: Movie? = null,
    val genres: String = "",
    val isInWatchList: Boolean = false
)