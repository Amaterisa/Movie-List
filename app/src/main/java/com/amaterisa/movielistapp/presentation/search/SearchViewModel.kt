package com.amaterisa.movielistapp.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _movieResult = MutableLiveData<List<Movie>>()
    val movieResult: LiveData<List<Movie>>
        get() = _movieResult

    private var currentSearchJob: Job? = null

    private val searchQuery = MutableStateFlow("")

    init {
        // Observe the search query with debounce
        viewModelScope.launch {
            searchQuery
                .debounce(300) // Wait for 300ms after user stops typing
                .collect { query ->
                    if (query.isNotEmpty()) {
                        searchMovie(query)
                    }
                }
        }
    }

    // Function to update the search query
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun searchMovie(name: String) {
        Log.d("TAG", "search $name")
        viewModelScope.launch {
            searchMovieUseCase.invoke(name).collect {
                _movieResult.postValue(it)
            }
        }
    }
}