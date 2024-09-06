package com.amaterisa.movielistapp.presentation.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ItemWatchListMovieBinding
import com.amaterisa.movielistapp.domain.model.WatchListMovie
import com.amaterisa.movielistapp.utils.MovieUtils.getImageUrl
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class WatchListAdapter(
    private val onRemoveClick: (WatchListMovie) -> Unit,
    private val onMarkClick: (WatchListMovie) -> Unit
) : RecyclerView.Adapter<WatchListAdapter.MovieViewHolder>() {
    private var movieList: MutableList<WatchListMovie> = mutableListOf()

    class MovieViewHolder(private val binding: ItemWatchListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: WatchListMovie,
            onRemoveClick: (WatchListMovie) -> Unit,
            onMarkClick: (WatchListMovie) -> Unit
        ) {
            binding.run {
                val url = getImageUrl(300, movie.posterPath)

                txvTitle.text = movie.title
                txvDescription.text = movie.overview

                updateButton(btnMark, movie.markWatched)

                Glide.with(root.context)
                    .load(url)
                    .into(movieImageView)

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
                    val icon = ContextCompat.getDrawable(context, R.drawable.ic_mark)
                    setBackgroundResource(R.drawable.outline_background)
                    backgroundTintList = ContextCompat.getColorStateList(context, R.color.gray)
                    text = context.getString(R.string.mark_watched_label)
                    setTextColor(txtColor)
                    setIconTintResource(R.color.font_color)
                    setIcon(icon)
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
        holder.bind(movieList[position], onRemoveClick, onMarkClick)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<WatchListMovie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun removeMovie(movie: WatchListMovie) {
        val position = movieList.indexOf(movie)
        if (position >= 0) {
            movieList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}