package com.amaterisa.movielistapp.presentation.main

import com.amaterisa.movielistapp.domain.model.Movie

interface IMainActivity {
    fun setupNavigationLayout(fragmentConfig: FragmentConfig)

    fun goToMovieDetails(movie: Movie)
}