package com.amaterisa.movielistapp.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentWatchListBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.adapter.LinearItemDecoration
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.presentation.main.MainActivity
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : BaseFragment() {

    companion object {
        const val TAG = "WatchListFragment"
    }

    private val viewModel: WatchListViewModel by viewModels()

    private val binding: FragmentWatchListBinding by lazy {
        FragmentWatchListBinding.inflate(layoutInflater)
    }

    private val watchListAdapter: WatchListAdapter by lazy {
        WatchListAdapter(
            { movie -> removeFromWatchList(movie) },
            { movie -> markMovie(movie) },
            { movie -> goToMovieDetails(movie) }
        )
    }

    override fun fragmentType() = FragmentConfig.WATCH_LIST

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
    }

    override fun onResume() {
        super.onResume()
        manageViews(isListEmpty = true, isLoading = true)
        viewModel.getWatchListMovies()
    }

    private fun setupBinding() {
        binding.run {
            if (moviesRv.adapter == null) {
                moviesRv.adapter = watchListAdapter
                val spaceInPixels = resources.getDimensionPixelSize(R.dimen.half_default_padding)
                moviesRv.addItemDecoration(LinearItemDecoration(spaceInPixels, true))

                moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (dy > 0) {
                            btnTop.visibility = View.VISIBLE
                        } else if (!recyclerView.canScrollVertically(-1)) {
                            btnTop.visibility = View.GONE
                        }
                    }
                })
            }

            btnTop.setOnClickListener {
                moviesRv.smoothScrollToPosition(0)
            }

            btnSearch.setOnClickListener {
                openSearch()
            }
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
        val listSize = watchListAdapter.removeMovie(movie)
        viewModel.removeFromWatchList(movie)
        manageViews(listSize <= 0)
    }

    private fun markMovie(movie: Movie) {
        viewModel.markWatchListMovie(movie)
    }

    private fun openSearch() {
        if (requireActivity() is MainActivity) {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.goToSearch()
        }
    }
}