package com.amaterisa.movielistapp.presentation.popularmovies

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentPopularMoviesBinding
import com.amaterisa.movielistapp.domain.common.ViewUtils.toVisibility
import com.amaterisa.movielistapp.presentation.adapter.GridSpaceItemDecoration
import com.amaterisa.movielistapp.presentation.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private val viewModel: PopularMoviesViewModel by viewModels()

    private val binding: FragmentPopularMoviesBinding by lazy {
        FragmentPopularMoviesBinding.inflate(layoutInflater)
    }

    private val movieListAdapter: MovieListAdapter by lazy {
        MovieListAdapter({}, {})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setupBinding()
        manageViews(true)
        viewModel.getPopularMovies()
    }

    private fun setupBinding() {
        binding.run {
            moviesRv.adapter = movieListAdapter
            //moviesRv.layoutManager = GridLayoutManager(this@PopularMoviesFragment.context, 3)

            val spaceInPixels = resources.getDimensionPixelSize(R.dimen.half_default_padding) // Define in dimens.xml or use a fixed value
            moviesRv.addItemDecoration(GridSpaceItemDecoration(spaceInPixels))
        }
    }

    private fun initObservers() {
        viewModel.movieResult.observe(viewLifecycleOwner) {
            movieListAdapter.setMovies(it)
            manageViews(false)
        }
    }

    private fun manageViews(isLoading: Boolean) {
        binding.run {
            moviesRv.toVisibility(!isLoading)
            progressBar.toVisibility(isLoading)
        }
    }
}