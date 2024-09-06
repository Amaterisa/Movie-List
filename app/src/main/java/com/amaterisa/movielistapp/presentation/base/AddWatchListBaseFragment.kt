package com.amaterisa.movielistapp.presentation.base

import com.amaterisa.movielistapp.domain.model.Movie

abstract class AddWatchListBaseFragment<VM : AddWatchListBaseViewModel> : BaseFragment() {
    abstract val viewModel: VM

    open fun onAddToWatchList(movie: Movie) {
        viewModel.saveToWatchList(movie)
    }
}