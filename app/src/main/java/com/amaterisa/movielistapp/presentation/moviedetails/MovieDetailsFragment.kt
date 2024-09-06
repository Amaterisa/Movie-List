package com.amaterisa.movielistapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.data.source.remote.movie.MovieDetailsResponse
import com.amaterisa.movielistapp.databinding.FragmentMovieDetailsBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.base.AddWatchListBaseFragment
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.utils.MovieUtils.getImageUrl
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MovieDetailsFragment : AddWatchListBaseFragment<MovieDetailsViewModel>() {

    companion object {
        const val TAG = "MovieDetailsFragment"
    }

    override val viewModel: MovieDetailsViewModel by viewModels()

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

        val movieId = arguments?.getLong("movieId")
        movieId?.let { viewModel.getMovieDetails(it) }

    }

    private fun initObservers() {
        viewModel.movieResult.observe(viewLifecycleOwner) {
            if (it != null) {
                setupBinding(it)
            } else {

            }
        }
    }

    private fun setupBinding(movie: Movie) {
        binding.run {
            title.text = movie.title
            description.text = movie.overview

            val url = getImageUrl(500, movie.backdropPath)
            Glide.with(root.context)
                .load(url)
                .into(imvMovie)

            imvMovie.visibility = View.VISIBLE

            releaseDateValue.text = movie.releaseDate
            val score = String.format(Locale.ROOT, "%.2f", movie.voteAverage.toFloat())
            scoreValue.text = getString(R.string.score_value, score)
            setupWatchListButton(movie.isInWatchList)

            btnWatchList.setOnClickListener {
                toggleWatchListMark(movie.isInWatchList)
            }
        }
    }

    private fun toggleWatchListMark(marked: Boolean) {
        viewModel.toggleWatchList(marked)
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