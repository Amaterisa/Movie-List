package com.amaterisa.movielistapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ItemMovieBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.utils.MovieUtils.getImageUrl
import com.amaterisa.movielistapp.utils.MovieUtils.loadImageWithGlide

class MovieListAdapter(
    private val imageWidth: Int,
    private val imageHeight: Int,
    private val onItemClick: (Movie) -> Unit,
    private val onWatchlistClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            imageWidth: Int,
            imageHeight: Int,
            onItemClick: (Movie) -> Unit,
            onWatchlistClick: (Movie) -> Unit
        ) {
            val url = getImageUrl(300, movie.posterPath)

            binding.run {
                val layoutParams = movieImageView.layoutParams
                layoutParams.width = imageWidth
                layoutParams.height = imageHeight
                movieImageView.layoutParams = layoutParams

                loadImageWithGlide(movieImageView, url)

                setButtonDrawable(addBtn, movie)

                root.setOnClickListener { onItemClick(movie) }

                addBtn.setOnClickListener {
                    onWatchlistClick(movie)
                    setButtonDrawable(addBtn, movie)
                }
            }
        }

        private fun setButtonDrawable(view: ImageView, movie: Movie) {
            val addDrawable = ContextCompat.getDrawable(view.context, R.drawable.remove_btn)
            val removeDrawable = ContextCompat.getDrawable(view.context, R.drawable.add_btn)
            if (movie.isInWatchList) {
                view.setImageDrawable(addDrawable)
            } else {
                view.setImageDrawable(removeDrawable)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], imageWidth, imageHeight, onItemClick, onWatchlistClick)
    }

    override fun getItemCount(): Int = movieList.size

    fun setMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
    }

    fun addToWatchList(movie: Movie) {
        val index = movieList.indexOfFirst { it.id == movie.id }
        if (index != -1) {
            movieList[index] = movie
            notifyItemChanged(index)
        }
    }

    fun getMovies(): List<Movie> = movieList.toList()
}
