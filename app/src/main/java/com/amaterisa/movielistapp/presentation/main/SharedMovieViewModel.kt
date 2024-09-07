package com.amaterisa.movielistapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaterisa.movielistapp.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedMovieViewModel @Inject constructor(): ViewModel() {
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> get() = _selectedMovie

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}