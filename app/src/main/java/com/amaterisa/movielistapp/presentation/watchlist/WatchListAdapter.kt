package com.amaterisa.movielistapp.presentation.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ItemWatchListMovieBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.utils.MovieUtils.getImageUrl
import com.amaterisa.movielistapp.presentation.utils.MovieUtils.loadImageWithGlide
import com.google.android.material.button.MaterialButton

class WatchListAdapter(
    private val onRemoveClick: (Movie) -> Unit,
    private val onMarkClick: (Movie) -> Unit,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<WatchListAdapter.MovieViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    class MovieViewHolder(private val binding: ItemWatchListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            onRemoveClick: (Movie) -> Unit,
            onMarkClick: (Movie) -> Unit,
            onItemClick: (Movie) -> Unit
        ) {
            binding.run {
                val url = getImageUrl(300, movie.posterPath)

                txvTitle.text = movie.title
                txvDescription.text = movie.overview

                updateButton(btnMark, movie.markWatched)

                loadImageWithGlide(movieImageView, url)

                root.setOnClickListener { onItemClick(movie) }

                btnRemove.setOnClickListener {
                    onRemoveClick(movie)
                }

                btnMark.setOnClickListener {
                    updateButton(btnMark, !movie.markWatched)
                    onMarkClick(movie)
                }
            }
        }

        private fun updateButton(button: MaterialButton, isMarked: Boolean) {
            button.run {
                if (isMarked) {
                    val txtColor = ContextCompat.getColor(context, R.color.dark_gray)
                    val icon = ContextCompat.getDrawable(context, R.drawable.ic_check)
                    text = context.getString(R.string.watched_label)
                    setBackgroundResource(R.drawable.button_rounded_background)
                    backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.cherry_dark)
                    setTextColor(txtColor)
                    setIconTintResource(R.color.dark_gray)
                    setIcon(icon)
                } else {
                    val txtColor = ContextCompat.getColor(context, R.color.font_color)
                    setBackgroundResource(R.drawable.outline_background)
                    backgroundTintList = ContextCompat.getColorStateList(context, R.color.gray)
                    text = context.getString(R.string.mark_watched_label)
                    setTextColor(txtColor)
                    setIconTintResource(R.color.font_color)
                    icon = null
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemWatchListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], onRemoveClick, onMarkClick, onItemClick)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun removeMovie(movie: Movie): Int {
        val position = movieList.indexOf(movie)
        if (position >= 0) {
            movieList.removeAt(position)
            notifyItemRemoved(position)
        }
        return movieList.size
    }
}