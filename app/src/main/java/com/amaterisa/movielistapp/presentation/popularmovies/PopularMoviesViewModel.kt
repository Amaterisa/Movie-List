package com.amaterisa.movielistapp.presentation.popularmovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {
    companion object {
        private const val TAG = "PopularMoviesViewModel"
    }

    private val _movieResult = MutableLiveData<List<Movie>>()
    val movieResult: LiveData<List<Movie>>
        get() = _movieResult

    fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collect {
                _movieResult.postValue(it)
                Log.d(TAG, "result $it")
            }
        }
    }
}