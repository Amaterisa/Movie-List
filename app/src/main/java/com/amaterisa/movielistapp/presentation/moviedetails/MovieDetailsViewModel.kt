package com.amaterisa.movielistapp.presentation.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetMovieGenresUseCase
import com.amaterisa.movielistapp.domain.usecase.GetWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import com.amaterisa.movielistapp.presentation.base.ManageWatchListBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getWatchListUseCase: GetWatchListUseCase,
    saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase,
    removeMovieFromWatchListUseCase: RemoveMovieFromWatchListUseCase
) : ManageWatchListBaseViewModel(saveMovieToWatchListUseCase, removeMovieFromWatchListUseCase) {

    private val _genresResult = MutableLiveData<List<Genre>>()
    val genresResult: LiveData<List<Genre>>
        get() = _genresResult

    private val _watchListResult = MutableLiveData<List<Movie>>()
    val watchListResult: LiveData<List<Movie>>
        get() = _watchListResult

    fun getWatchListMovies() {
        viewModelScope.launch {
            getWatchListUseCase.invoke().collect {
                _watchListResult.postValue(it)
            }
        }
    }

    fun toggleWatchList(movie: Movie) {
        viewModelScope.launch {
            if (isInWatchList(movie)) {
                removeFromWatchList(movie.id)
            } else {
                saveToWatchList(movie)
            }
        }
    }

    fun getMovieGenres() {
        viewModelScope.launch {
            getMovieGenresUseCase.invoke().collect {
                _genresResult.postValue(it)
            }
        }
    }

    fun isInWatchList(movie: Movie): Boolean {
        val isInWatchList = watchListResult.value?.any { it.id == movie.id }
        return isInWatchList ?: false
    }
}