package com.amaterisa.movielistapp.presentation.popularmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment() {
    companion object {
        const val TAG = "PopularMoviesFragment"
    }

    private val viewModel: PopularMoviesViewModel by viewModels()

    override fun fragmentType() = FragmentConfig.POPULAR_MOVIES

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return buildComposeView()
    }

    private fun buildComposeView(): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    LaunchedEffect(key1 = Unit) {
                        viewModel.sendAction(PopularMoviesAction.Action.FetchInitialData)


                        viewModel.effect.collect { currentEffect ->
                            currentEffect.let { effect ->
                                when (effect) {
                                    is PopularMoviesUiEffect.NavigateToMovieDetails -> {
                                        navigateToMovieDetails(movie = effect.movie)
                                    }
                                }
                            }
                        }
                    }

                    val state by viewModel.state.observeAsState()

                    PopularMoviesScreen(
                        checkNotNull(state),
                        viewModel::sendAction
                    )
                }
            }
        }
    }

    private fun navigateToMovieDetails(movie: Movie) {
        goToMovieDetails(movie)
    }
}