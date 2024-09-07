package com.amaterisa.movielistapp.presentation.base

import com.amaterisa.movielistapp.domain.model.Movie

abstract class ManageWatchListBaseFragment<VM : ManageWatchListBaseViewModel> : BaseFragment() {
    abstract val viewModel: VM

    open fun onAddToWatchList(movie: Movie) {
        viewModel.saveToWatchList(movie)
    }

    open fun removeFromWatchList(id: Long) {
        viewModel.removeFromWatchList(id)
    }
}