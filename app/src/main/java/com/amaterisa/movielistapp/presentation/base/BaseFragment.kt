package com.amaterisa.movielistapp.presentation.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.presentation.main.IMainActivity
import com.amaterisa.movielistapp.presentation.main.SharedMovieViewModel

abstract class BaseFragment: Fragment() {

    val sharedViewModel: SharedMovieViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        if (requireActivity() is IMainActivity) {
            val mainActivity = requireActivity() as IMainActivity
            mainActivity.setupNavigationLayout(fragmentType())
        }
    }

    abstract fun fragmentType(): FragmentConfig

    fun goToMovieDetails(movie: Movie) {
        if (requireActivity() is IMainActivity) {
            val mainActivity = requireActivity() as IMainActivity
            mainActivity.goToMovieDetails(movie)
            sharedViewModel.selectMovie(movie)
        }
    }
}