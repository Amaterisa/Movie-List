package com.amaterisa.movielistapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _movieResult = MutableLiveData<Resource<List<Movie>>>()
    val movieResult: LiveData<Resource<List<Movie>>>
        get() = _movieResult

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .collect { query ->
                    if (query.isNotEmpty()) {
                        searchMovie(query)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    private fun searchMovie(name: String) {
        viewModelScope.launch {
            searchMovieUseCase.invoke(name).collect {
                _movieResult.postValue(it)
            }
        }
    }
}