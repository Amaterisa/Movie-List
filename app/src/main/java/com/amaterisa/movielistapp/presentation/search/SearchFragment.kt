package com.amaterisa.movielistapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentSearchBinding
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.adapter.LinearItemDecoration
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    companion object {
        const val TAG = "SearchFragment"
    }

    private val viewModel: SearchViewModel by viewModels()

    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    private val adapter: SearchMovieAdapter by lazy {
        SearchMovieAdapter { movie -> goToMovieDetails(movie) }
    }

    override fun fragmentType() = FragmentConfig.SEARCH

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
        setupSearchView()
    }

    private fun setupBinding() {
        binding.run {
            if (moviesRv.adapter == null) {
                moviesRv.adapter = adapter
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

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.updateSearchQuery(it)
                    }
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        viewModel.updateSearchQuery(it)
                    }
                    return true
                }
            })

            errorLayout.btnRetry.setOnClickListener {
                viewModel.updateSearchQuery(searchView.query.toString())
            }
        }
    }

    private fun initObservers() {
        viewModel.movieResult.observe(viewLifecycleOwner) {
            handleMovieSearchResult(it)
        }
    }

    private fun handleMovieSearchResult(resource: Resource<List<Movie>>) {
        when (resource) {
            is Resource.Success -> {
                val movies = resource.data
                adapter.setMovies(movies)
                manageViews(movies.isEmpty())
            }

            else -> {
                manageViews(isEmpty = true, hasError = true)
            }
        }
    }

    private fun manageViews(isEmpty: Boolean, hasError: Boolean = false) {
        binding.run {
            moviesRv.toVisibility(!isEmpty && !hasError)
            noItemsLayout.toVisibility(isEmpty && !hasError)
            errorLayout.errorLayout.toVisibility(hasError)
        }
    }

    private fun setupSearchView() {
        binding.searchView.run {
            val searchTextView = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.font_color
                )
            )
            searchTextView.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            val searchIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.cherry_dark))

            val closeIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            closeIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.cherry_dark))
        }
    }
}