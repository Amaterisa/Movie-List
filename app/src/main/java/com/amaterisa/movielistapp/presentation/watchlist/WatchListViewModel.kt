package com.amaterisa.movielistapp.presentation.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.MarkWatchListMovieUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListUseCase,
    private val removeMovieFromWatchListUseCase: RemoveMovieFromWatchListUseCase,
    private val markWatchListMovieUseCase: MarkWatchListMovieUseCase
) : ViewModel() {
    private val _movieResult = MutableLiveData<List<Movie>>()
    val movieResult: LiveData<List<Movie>>
        get() = _movieResult

    init {
        getWatchListMovies()
    }

    fun getWatchListMovies() {
        viewModelScope.launch {
            getWatchListUseCase.invoke().collect {
                _movieResult.postValue(it)
            }
        }
    }

    fun removeFromWatchList(movie: Movie) {
        viewModelScope.launch {
            removeMovieFromWatchListUseCase.invoke(movie.id)
        }
    }

    fun markWatchListMovie(movie: Movie) {
        viewModelScope.launch {
            markWatchListMovieUseCase.invoke(movie)
        }
    }
}