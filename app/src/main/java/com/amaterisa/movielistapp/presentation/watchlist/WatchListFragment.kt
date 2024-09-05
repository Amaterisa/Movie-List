package com.amaterisa.movielistapp.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentWatchListBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.adapter.LinearItemDecoration
import com.amaterisa.movielistapp.presentation.adapter.MovieListAdapter
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : Fragment() {

    companion object {
        const val TAG = "WatchListFragment"
    }

    private val viewModel: WatchListViewModel by viewModels()

    private val binding: FragmentWatchListBinding by lazy {
        FragmentWatchListBinding.inflate(layoutInflater)
    }

    private val watchListAdapter: WatchListAdapter by lazy {
        WatchListAdapter { movie -> removeFromWatchList(movie) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
        initObservers()
        manageViews(isListEmpty = true, isLoading = true)
        //viewModel.getWatchListMovies()
    }

    private fun setupBinding() {
        binding.run {
            moviesRv.adapter = watchListAdapter
            val spaceInPixels = resources.getDimensionPixelSize(R.dimen.default_padding)
            moviesRv.addItemDecoration(LinearItemDecoration(spaceInPixels, true))
        }
    }

    private fun initObservers() {
        viewModel.movieResult.observe(viewLifecycleOwner) {
            watchListAdapter.setMovies(it)
            manageViews(it.isEmpty())
        }
    }

    private fun manageViews(isListEmpty: Boolean, isLoading: Boolean = false) {
        binding.run {
            noItemsLayout.toVisibility(isListEmpty && !isLoading)
            moviesRv.toVisibility(!isListEmpty && !isLoading)
            progressBar.toVisibility(isLoading)
        }
    }

    private fun removeFromWatchList(movie: Movie) {
        watchListAdapter.removeMovie(movie)
        viewModel.removeFromWatchList(movie)
    }
}