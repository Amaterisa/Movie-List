package com.amaterisa.movielistapp.presentation.popularmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentPopularMoviesBinding
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.adapter.GridSpaceItemDecoration
import com.amaterisa.movielistapp.presentation.adapter.MovieListAdapter
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.presentation.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment() {
    companion object {
        const val TAG = "PopularMoviesFragment"
    }

    private val viewModel: PopularMoviesViewModel by viewModels()

    private val binding: FragmentPopularMoviesBinding by lazy {
        FragmentPopularMoviesBinding.inflate(layoutInflater)
    }

    private val movieListAdapter: MovieListAdapter by lazy {
        MovieListAdapter(
            resources.getDimensionPixelSize(R.dimen.top_movie_width),
            resources.getDimensionPixelSize(R.dimen.top_movie_height)
        ) { movie -> goToMovieDetails(movie) }
    }

    override fun fragmentType() = FragmentConfig.POPULAR_MOVIES

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
    }

    private fun setupBinding() {
        binding.run {
            if (moviesRv.adapter == null) {
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
            }

            btnTop.setOnClickListener {
                moviesRv.smoothScrollToPosition(0)
            }

            errorLayout.btnRetry.setOnClickListener {
                viewModel.getPopularMovies()
            }
        }
    }

    private fun initObservers() {
        viewModel.movieResult.observe(viewLifecycleOwner) {
            handleMoviesResource(it)
        }
    }

    private fun handleMoviesResource(resource: Resource<List<Movie>>) {
        when (resource) {
            is Resource.Loading -> {
                manageViews(true)
            }

            is Resource.Success -> {
                movieListAdapter.setMovies(resource.data)
                manageViews(false)
            }

            is Resource.Error -> {
                manageViews(isLoading = false, hasError = true)
            }
        }
    }

    private fun manageViews(isLoading: Boolean, hasError: Boolean = false) {
        binding.run {
            moviesRv.toVisibility(!isLoading && !hasError)
            progressBar.toVisibility(isLoading && !hasError)
            errorLayout.errorLayout.toVisibility(hasError)
        }
    }
}