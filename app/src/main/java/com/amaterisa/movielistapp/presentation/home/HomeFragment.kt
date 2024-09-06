package com.amaterisa.movielistapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentHomeBinding
import com.amaterisa.movielistapp.presentation.adapter.LinearItemDecoration
import com.amaterisa.movielistapp.presentation.base.AddWatchListBaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AddWatchListBaseFragment<HomeViewModel>() {

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
            {},
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
        manageViews(true)
    }

    private fun initObservers() {
        viewModel.genreListResult.observe(viewLifecycleOwner) {
            for (genre in it) {
                viewModel.getMoviesByGenre(genre)
            }
        }

        viewModel.movieResult.observe(viewLifecycleOwner) {
            manageViews(false)
            moviesByGenreAdapter.setMoviesByGenre(it)
        }
    }

    private fun setupBinding() {
        binding.run {
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

            btnTop.setOnClickListener {
                genresRv.smoothScrollToPosition(0)
            }
        }
    }

    private fun manageViews(isLoading: Boolean) {
        binding.run {
            genresRv.toVisibility(!isLoading)
            progressBar.toVisibility(isLoading)
        }
    }
}