package com.amaterisa.movielistapp.presentation.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetPopularMoviesUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import com.amaterisa.movielistapp.presentation.base.ManageWatchListBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase,
    removeMovieFromWatchListUseCase: RemoveMovieFromWatchListUseCase
) : ManageWatchListBaseViewModel(saveMovieToWatchListUseCase, removeMovieFromWatchListUseCase) {
    companion object {
        private const val TAG = "PopularMoviesViewModel"
    }

    private val _movieResult = MutableLiveData<Resource<List<Movie>>>()
    val movieResult: LiveData<Resource<List<Movie>>>
        get() = _movieResult

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke().collect {
                _movieResult.postValue(it)
            }
        }
    }
}