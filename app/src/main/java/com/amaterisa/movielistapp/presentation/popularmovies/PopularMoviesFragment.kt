package com.amaterisa.movielistapp.presentation.popularmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentPopularMoviesBinding
import com.amaterisa.movielistapp.presentation.adapter.GridSpaceItemDecoration
import com.amaterisa.movielistapp.presentation.adapter.MovieListAdapter
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<PopularMoviesViewModel>() {
    companion object {
        const val TAG = "PopularMoviesFragment"
    }

    override val viewModel: PopularMoviesViewModel by viewModels()

    private val binding: FragmentPopularMoviesBinding by lazy {
        FragmentPopularMoviesBinding.inflate(layoutInflater)
    }

    private val movieListAdapter: MovieListAdapter by lazy {
        MovieListAdapter(
            resources.getDimensionPixelSize(R.dimen.top_movie_width),
            resources.getDimensionPixelSize(R.dimen.top_movie_height),
            {},
            { movie -> onAddToWatchList(movie) })
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
            val spaceInPixels = resources.getDimensionPixelSize(R.dimen.half_default_padding)
            moviesRv.addItemDecoration(GridSpaceItemDecoration(spaceInPixels))

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

            btnTop.setOnClickListener {
                moviesRv.smoothScrollToPosition(0)
            }
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