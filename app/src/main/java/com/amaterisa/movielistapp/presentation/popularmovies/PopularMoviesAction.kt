package com.amaterisa.movielistapp.presentation.popularmovies

import com.amaterisa.movielistapp.domain.model.Movie

interface PopularMoviesAction {
    fun sendAction(action: Action)

    sealed class Action {
        data object FetchInitialData : Action()
        data object ClickRetry : Action()
        data class ClickMovie(val movie: Movie) : Action()
    }
}