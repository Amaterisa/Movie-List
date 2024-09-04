package com.amaterisa.movielistapp.presentation.base

import android.util.Log
import androidx.fragment.app.Fragment
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.popularmovies.PopularMoviesFragment.Companion.TAG

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    abstract val viewModel: VM

    open fun onAddToWatchList(movie: Movie) {
        Log.d(TAG, "Add movie to watchlist: $movie")
        viewModel.saveToWatchList(movie)
    }
}