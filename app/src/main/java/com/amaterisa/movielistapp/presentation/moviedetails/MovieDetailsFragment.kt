package com.amaterisa.movielistapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentMovieDetailsBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.utils.MovieUtils
import com.amaterisa.movielistapp.utils.MovieUtils.getImageUrl
import com.amaterisa.movielistapp.utils.MovieUtils.loadImageWithGlide
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        sharedViewModel.selectedMovie.observe(viewLifecycleOwner) { movie ->
            setupBinding(movie)
            viewModel.getMovieGenres()
            viewModel.getWatchListMovies()
        }

        viewModel.genresResult.observe(viewLifecycleOwner) { genres ->
            val movie = sharedViewModel.selectedMovie
            movie.value?.let {
                if (it.genreIds.isNotEmpty()) {
                    val genresNames = MovieUtils.getGenreNames(it.genreIds, genres)
                    binding.genresValue.text = genresNames
                }
            }
        }

        viewModel.watchListResult.observe(viewLifecycleOwner) { watchList ->
            val isInWatchList = sharedViewModel.selectedMovie.value?.let {
                viewModel.isInWatchList(it)
            } ?: false

            setupWatchListButton(isInWatchList)
        }
    }

    private fun setupBinding(movie: Movie) {
        binding.run {
            title.text = movie.title
            description.text = movie.overview

            val url = getImageUrl(500, movie.backdropPath)
            loadImageWithGlide(imvMovie, url)

            imvMovie.visibility = View.VISIBLE

            releaseDateValue.text = movie.releaseDate
            val score = String.format(Locale.ROOT, "%.2f", movie.voteAverage.toFloat())
            scoreValue.text = getString(R.string.score_value, score)

            btnWatchList.setOnClickListener {
                toggleWatchListMark(movie)
            }
        }
    }

    private fun toggleWatchListMark(movie: Movie) {
        viewModel.toggleWatchList(movie)
    }

    private fun setupWatchListButton(marked: Boolean) {
        binding.btnWatchList.run {
            if (marked) {
                val txtColor = ContextCompat.getColor(context, R.color.dark_gray)
                val icon = ContextCompat.getDrawable(context, R.drawable.ic_list_selected)
                text = context.getString(R.string.added_watch_list_label)
                setBackgroundResource(R.drawable.button_rounded_background)
                backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.cherry_dark)
                setTextColor(txtColor)
                setIconTintResource(R.color.dark_gray)
                setIcon(icon)
            } else {
                val txtColor = ContextCompat.getColor(context, R.color.font_color)
                val icon = ContextCompat.getDrawable(context, R.drawable.ic_add)
                setBackgroundResource(R.drawable.outline_background)
                backgroundTintList = ContextCompat.getColorStateList(context, R.color.gray)
                text = context.getString(R.string.add_watch_list_label)
                setTextColor(txtColor)
                setIconTintResource(R.color.font_color)
                setIcon(icon)
            }
        }
    }
}