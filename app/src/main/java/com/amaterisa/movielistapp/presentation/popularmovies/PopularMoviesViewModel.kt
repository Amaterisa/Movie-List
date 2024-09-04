package com.amaterisa.movielistapp.presentation.popularmovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import com.amaterisa.movielistapp.domain.usecase.GetPopularMoviesUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "PopularMoviesViewModel"
    }

    private val _movieResult = MutableLiveData<List<Movie>>()
    val movieResult: LiveData<List<Movie>>
        get() = _movieResult

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke().collect {
                _movieResult.postValue(it)
                Log.d(TAG, "result $it")
            }
        }
    }

    fun saveToWatchList(movie: Movie) {
        saveMovieToWatchListUseCase.invoke(movie)
    }
}