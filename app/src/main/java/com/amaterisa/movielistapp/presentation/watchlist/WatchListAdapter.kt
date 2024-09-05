package com.amaterisa.movielistapp.presentation.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.databinding.ItemWatchListMovieBinding
import com.amaterisa.movielistapp.domain.model.Movie

class WatchListAdapter(
    private val onRemoveClick: (Movie) -> Unit
) : RecyclerView.Adapter<WatchListAdapter.MovieViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    class MovieViewHolder(private val binding: ItemWatchListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onRemoveClick: (Movie) -> Unit) {
            binding.run {
                txvTitle.text = movie.title
                txvDescription.text = movie.overview

                btnRemove.setOnClickListener {
                    onRemoveClick(movie)
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
        holder.bind(movieList[position], onRemoveClick)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
    }

    fun removeMovie(movie: Movie) {
        val position = movieList.indexOf(movie)
        if (position >= 0) {
            movieList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}