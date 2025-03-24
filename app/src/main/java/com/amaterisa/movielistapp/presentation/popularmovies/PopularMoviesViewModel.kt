package com.amaterisa.movielistapp.presentation.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.domain.usecase.GetPopularMoviesUseCase
import com.amaterisa.movielistapp.presentation.base.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel(), PopularMoviesAction {
    companion object {
        private const val TAG = "PopularMoviesViewModel"
    }

    private var _effect = MutableSharedFlow<PopularMoviesUiEffect>()
    val effect = _effect.asSharedFlow()

    private var _state = MutableLiveData<MoviesState>(MoviesState.Initial)
    val state: LiveData<MoviesState> get() = _state

    fun fetchPopularMovies() {
        _state.value = MoviesState.Loading(getCurrentStateModel())
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke().collect {
                handleMoviesResource(it)
            }
        }
    }

    override fun sendAction(action: PopularMoviesAction.Action) {
        viewModelScope.launch {
            when (action) {
                is PopularMoviesAction.Action.ClickMovie -> {
                    _effect.emit(PopularMoviesUiEffect.NavigateToMovieDetails(action.movie))
                }

                PopularMoviesAction.Action.FetchInitialData, PopularMoviesAction.Action.ClickRetry -> {
                    fetchPopularMovies()
                }
            }
        }
    }

    private fun handleMoviesResource(resource: Resource<List<Movie>>) {
        when (resource) {
            is Resource.Loading -> {
                dispatchLoadingState()
            }

            is Resource.Success -> {
                if (resource.data.isEmpty()) {
                    dispatchErrorState()
                } else {
                    _state.value = MoviesState.Resume(
                        getCurrentStateModel().copy(
                            movies = resource.data
                        )
                    )
                }
            }

            is Resource.Error -> {
                dispatchErrorState()
            }
        }
    }

    private fun dispatchLoadingState() {
        _state.value = MoviesState.Loading(getCurrentStateModel())
    }

    private fun dispatchErrorState() {
        _state.value = MoviesState.Error(
            getCurrentStateModel().copy(
                movies = emptyList()
            )
        )
    }

    private fun getCurrentStateModel() = checkNotNull(_state.value?.uiModel)
}