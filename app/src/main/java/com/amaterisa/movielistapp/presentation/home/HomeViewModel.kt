package com.amaterisa.movielistapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetGenreUseCase
import com.amaterisa.movielistapp.domain.usecase.GetMoviesByGenreUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import com.amaterisa.movielistapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenreUseCase: GetGenreUseCase,
    private val getMoviesByGetGenreUseCase: GetMoviesByGenreUseCase,
    saveMovieToWatchListUseCase: SaveMovieToWatchListUseCase
) : BaseViewModel(saveMovieToWatchListUseCase) {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _genreListResult = MutableLiveData<List<Genre>>()
    val genreListResult: LiveData<List<Genre>>
        get() = _genreListResult

    private val _movieResult = MutableLiveData<Pair<Genre, List<Movie>>>()
    val movieResult: LiveData<Pair<Genre, List<Movie>>>
        get() = _movieResult

    fun getGenreList() {
        viewModelScope.launch {
            getGenreUseCase.invoke().collect {
                _genreListResult.postValue(it)
            }
        }
    }

    fun getMoviesByGenre(genre: Genre) {
        viewModelScope.launch {
            getMoviesByGetGenreUseCase.invoke(genre).collect {
                val pair = Pair(genre, it)
                _movieResult.postValue(pair)
            }
        }
    }
}