package com.amaterisa.movielistapp.presentation.popularmovies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.presentation.base.ErrorScreen
import com.amaterisa.movielistapp.presentation.base.MovieItem
import com.amaterisa.movielistapp.presentation.base.MoviesState

@Composable
fun PopularMoviesScreen(
    state: MoviesState,
    sendAction: (PopularMoviesAction.Action) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (state) {
            is MoviesState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    color = colorResource(id = R.color.cherry_dark)
                )
            }

            is MoviesState.Error -> {
                ErrorScreen(onRetry = { sendAction(PopularMoviesAction.Action.ClickRetry) })
            }

            is MoviesState.Resume, MoviesState.Initial -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.uiModel.movies.size) { index ->
                        val movie = state.uiModel.movies[index]
                        MovieItem(movie = movie, onClick = { sendAction(PopularMoviesAction.Action.ClickMovie(it)) })
                    }
                }
            }
        }
    }
}