package com.amaterisa.movielistapp.presentation.base

import androidx.lifecycle.ViewModel
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase

abstract class BaseViewModel(
    private val saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase
): ViewModel() {
    fun saveToWatchList(movie: Movie) {
        saveMovieToWatchListUseCase.invoke(movie)
    }
}