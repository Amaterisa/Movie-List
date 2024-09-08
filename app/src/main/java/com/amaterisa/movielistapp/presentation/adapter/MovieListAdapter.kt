package com.amaterisa.movielistapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.databinding.ItemMovieBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.utils.MovieUtils.getImageUrl
import com.amaterisa.movielistapp.presentation.utils.MovieUtils.loadImageWithGlide

class MovieListAdapter(
    private val imageWidth: Int,
    private val imageHeight: Int,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            imageWidth: Int,
            imageHeight: Int,
            onItemClick: (Movie) -> Unit
        ) {
            val url = getImageUrl(300, movie.posterPath)

            binding.run {
                val layoutParams = movieImageView.layoutParams
                layoutParams.width = imageWidth
                layoutParams.height = imageHeight
                movieImageView.layoutParams = layoutParams

                loadImageWithGlide(movieImageView, url)

                root.setOnClickListener { onItemClick(movie) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], imageWidth, imageHeight, onItemClick)
    }

    override fun getItemCount(): Int = movieList.size

    fun setMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
    }
}
