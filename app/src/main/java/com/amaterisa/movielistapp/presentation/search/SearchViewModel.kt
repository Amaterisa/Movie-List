package com.amaterisa.movielistapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _movieResult = MutableLiveData<List<Movie>>()
    val movieResult: LiveData<List<Movie>>
        get() = _movieResult

    fun searchMovie(name: String) {
        viewModelScope.launch {
            searchMovieUseCase.invoke(name).collect {
                _movieResult.postValue(it)
            }
        }
    }
}