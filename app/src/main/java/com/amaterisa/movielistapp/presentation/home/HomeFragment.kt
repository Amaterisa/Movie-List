package com.amaterisa.movielistapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentHomeBinding
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.adapter.LinearItemDecoration
import com.amaterisa.movielistapp.presentation.base.ManageWatchListBaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ManageWatchListBaseFragment<HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"
    }

    override val viewModel: HomeViewModel by viewModels()

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val moviesByGenreAdapter: MoviesByGenreAdapter by lazy {
        MoviesByGenreAdapter(
            resources.getDimensionPixelSize(R.dimen.home_movie_width),
            resources.getDimensionPixelSize(R.dimen.home_movie_height),
            { movie -> goToMovieDetails(movie) },
            { movie -> onAddToWatchList(movie) })
    }

    override fun fragmentType() = FragmentConfig.HOME

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
        viewModel.getGenreList()
    }

    private fun initObservers() {
        viewModel.genreListResult.observe(viewLifecycleOwner) {
            handleGenreResource(it)
        }

        viewModel.movieResult.observe(viewLifecycleOwner) {
            handleMoviesResource(it)
        }
    }

    private fun setupBinding() {
        binding.run {
            if (genresRv.adapter == null) {
                genresRv.adapter = moviesByGenreAdapter
                val spaceInPixels = resources.getDimensionPixelSize(R.dimen.default_padding)
                genresRv.addItemDecoration(LinearItemDecoration(spaceInPixels, true))

                genresRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                genresRv.smoothScrollToPosition(0)
            }

            errorLayout.btnRetry.setOnClickListener {
                viewModel.getGenreList()
            }
        }
    }

    private fun handleMoviesResource(resource: Resource<Map<Genre, List<Movie>>>) {
        when (resource) {
            is Resource.Loading -> {
                manageViews(true)
            }

            is Resource.Success -> {
                val movieMap = resource.data
                moviesByGenreAdapter.setMoviesByGenre(movieMap)
                manageViews(isLoading = false, hasError = false)
            }

            is Resource.Error -> {
                manageViews(isLoading = false, hasError = true)
            }
        }
    }

    private fun handleGenreResource(resource: Resource<List<Genre>>) {
        when (resource) {
            is Resource.Loading -> {
                manageViews(true)
            }

            is Resource.Success -> {
                viewModel.getMoviesByGenre(resource.data)
            }

            is Resource.Error -> {
                manageViews(isLoading = false, hasError = true)
            }
        }
    }

    private fun manageViews(isLoading: Boolean = false, hasError: Boolean = false) {
        binding.run {
            genresRv.toVisibility(!isLoading && !hasError)
            progressBar.toVisibility(isLoading && !hasError)
            errorLayout.errorLayout.toVisibility(hasError)
        }
    }
}