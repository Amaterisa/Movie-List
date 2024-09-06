package com.amaterisa.movielistapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import kotlinx.coroutines.launch

abstract class ManageWatchListBaseViewModel(
    private val saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase,
    private val removeMovieFromWatchListUseCase: RemoveMovieFromWatchListUseCase
): ViewModel() {
    fun saveToWatchList(movie: Movie) {
        viewModelScope.launch {
            saveMovieToWatchListUseCase.invoke(movie)
        }
    }

    fun removeFromWatchList(id: Long) {
        viewModelScope.launch {
            removeMovieFromWatchListUseCase.invoke(id)
        }
    }
}