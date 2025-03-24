package com.amaterisa.movielistapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
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
class MovieDetailsFragment : BaseFragment() {

    companion object {
        const val TAG = "MovieDetailsFragment"
    }

    private val viewModel: MovieDetailsViewModel by viewModels()
    private var movie: Movie? = null

    override fun fragmentType() = FragmentConfig.MOVIE_DETAILS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return buildComposeView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun buildComposeView(): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val state by viewModel.state.observeAsState()

                    state?.let {
                        MovieDetailsScreen(
                            state = it,
                            onWatchListClick = {
                                viewModel.toggleWatchList()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun initObservers() {
        sharedViewModel.selectedMovie.observe(viewLifecycleOwner) {
            viewModel.setMovie(it)
            viewModel.getMovieGenres(it)
            viewModel.getWatchListMovies(it)
        }
    }
}