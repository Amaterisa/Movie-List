package com.amaterisa.movielistapp.presentation.main

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var currentFragment: FragmentConfig = FragmentConfig.HOME
    var backStack: MutableList<FragmentConfig> = mutableListOf()
}