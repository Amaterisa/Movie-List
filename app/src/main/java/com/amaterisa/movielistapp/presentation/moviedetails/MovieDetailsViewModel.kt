package com.amaterisa.movielistapp.presentation.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetGenresUseCase
import com.amaterisa.movielistapp.domain.usecase.GetWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import com.amaterisa.movielistapp.presentation.utils.MovieUtils
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

    private var watchList = mutableListOf<Movie>()

    private var _state = MutableLiveData<MovieDetailsState>(
        MovieDetailsState.Resume(
            MovieDetailsUiModel()
        )
    )
    val state: LiveData<MovieDetailsState> get() = _state

    fun getWatchListMovies(movie: Movie) {
        viewModelScope.launch {
            getWatchListUseCase.invoke().collect {
                watchList = it.toMutableList()
                _state.value = MovieDetailsState.Resume(
                    getCurrentStateModel().copy(
                        isInWatchList = isInWatchList(movie)
                    )
                )
            }
        }
    }

    fun toggleWatchList() {
        val movie = getCurrentStateModel().movie
        movie?.let {
            viewModelScope.launch {
                if (isInWatchList(movie)) {
                    removeMovieFromWatchListUseCase.invoke(movie.id)
                    _state.value = MovieDetailsState.Resume(
                        getCurrentStateModel().copy(
                            isInWatchList = false
                        )
                    )
                } else {
                    saveMovieToWatchListUseCase.invoke(movie)
                    _state.value = MovieDetailsState.Resume(
                        getCurrentStateModel().copy(
                            isInWatchList = true
                        )
                    )
                }
            }
        }
    }

    fun setMovie(movie: Movie) {
        _state.value = MovieDetailsState.Resume(
            getCurrentStateModel().copy(
                movie = movie
            )
        )
    }

    fun getMovieGenres(movie: Movie) {
        viewModelScope.launch {
            getGenresUseCase.invoke().collect {
                if (it is Resource.Success) {
                    _state.value = MovieDetailsState.Resume(
                        getCurrentStateModel().copy(
                            genres = MovieUtils.getGenreNames(movie.genreIds, it.data)
                        )
                    )
                }
            }
        }
    }

    fun isInWatchList(movie: Movie): Boolean {
        val isInWatchList = watchList.any { it.id == movie.id }
        return isInWatchList ?: false
    }

    private fun getCurrentStateModel() = checkNotNull(_state.value?.uiModel)
}