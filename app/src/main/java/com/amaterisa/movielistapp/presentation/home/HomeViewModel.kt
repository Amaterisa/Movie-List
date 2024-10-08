package com.amaterisa.movielistapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetGenresUseCase
import com.amaterisa.movielistapp.domain.usecase.GetMoviesByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGetGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _genreListResult = MutableLiveData<Resource<List<Genre>>>()
    val genreListResult: LiveData<Resource<List<Genre>>>
        get() = _genreListResult

    private val _movieResult = MutableLiveData<Resource<Map<Genre, List<Movie>>>>()
    val movieResult: LiveData<Resource<Map<Genre, List<Movie>>>>
        get() = _movieResult

    init {
        getGenreList()
    }

    fun getGenreList() {
        viewModelScope.launch {
            getGenresUseCase.invoke().collect {
                _genreListResult.postValue(it)
                if (it is Resource.Success) {
                    getMoviesByGenre(it.data)
                }
            }
        }
    }

    private fun getMoviesByGenre(genres: List<Genre>) {
        viewModelScope.launch {
            getMoviesByGetGenreUseCase.invoke(genres).collect {
                _movieResult.postValue(it)
            }
        }
    }
}