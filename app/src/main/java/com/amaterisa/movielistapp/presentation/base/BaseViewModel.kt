package com.amaterisa.movielistapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase
): ViewModel() {
    fun saveToWatchList(movie: Movie) {
        viewModelScope.launch {
            saveMovieToWatchListUseCase.invoke(movie)
        }
    }
}