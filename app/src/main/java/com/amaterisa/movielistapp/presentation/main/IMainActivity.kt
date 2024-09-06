package com.amaterisa.movielistapp.presentation.main

interface IMainActivity {
    fun setupNavigationLayout(fragmentConfig: FragmentConfig)

    fun goToMovieDetails(id: Long)
}