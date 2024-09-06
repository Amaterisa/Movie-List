package com.amaterisa.movielistapp.presentation.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetMovieDetailsUseCase
import com.amaterisa.movielistapp.domain.usecase.GetMovieGenresUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import com.amaterisa.movielistapp.presentation.base.ManageWatchListBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase,
    removeMovieFromWatchListUseCase: RemoveMovieFromWatchListUseCase
) : ManageWatchListBaseViewModel(saveMovieToWatchListUseCase, removeMovieFromWatchListUseCase) {

    private val _movieResult = MutableLiveData<Movie?>()
    val movieResult: LiveData<Movie?>
        get() = _movieResult

    private val _genresResult = MutableLiveData<List<Genre>>()
    val genresResult: LiveData<List<Genre>>
        get() = _genresResult

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            getMovieDetailsUseCase.invoke(id).collect {
                _movieResult.postValue(it)
            }
        }
    }

    fun toggleWatchList(marked: Boolean) {
        viewModelScope.launch {
            movieResult.value?.let { movie ->
                if (marked) {
                    removeFromWatchList(movie.id)
                } else {
                    saveToWatchList(movie)
                }
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
}