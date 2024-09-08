package com.amaterisa.movielistapp.presentation.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetGenresUseCase
import com.amaterisa.movielistapp.domain.usecase.GetWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getWatchListUseCase: GetWatchListUseCase,
    private val saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase,
    private val removeMovieFromWatchListUseCase: RemoveMovieFromWatchListUseCase
) : ViewModel() {

    private val _genresResult = MutableLiveData<Resource<List<Genre>>>()
    val genresResult: LiveData<Resource<List<Genre>>>
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
                removeMovieFromWatchListUseCase.invoke(movie.id)
            } else {
                saveMovieToWatchListUseCase.invoke(movie)
            }
        }
    }

    fun getMovieGenres() {
        viewModelScope.launch {
            getGenresUseCase.invoke().collect {
                _genresResult.postValue(it)
            }
        }
    }

    fun isInWatchList(movie: Movie): Boolean {
        val isInWatchList = watchListResult.value?.any { it.id == movie.id }
        return isInWatchList ?: false
    }
}