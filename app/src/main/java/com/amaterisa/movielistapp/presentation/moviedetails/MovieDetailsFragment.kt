package com.amaterisa.movielistapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentMovieDetailsBinding
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    companion object {
        const val TAG = "MovieDetailsFragment"
    }

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val binding: FragmentMovieDetailsBinding by lazy {
        FragmentMovieDetailsBinding.inflate(layoutInflater)
    }

    override fun fragmentType() = FragmentConfig.MOVIE_DETAILS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}