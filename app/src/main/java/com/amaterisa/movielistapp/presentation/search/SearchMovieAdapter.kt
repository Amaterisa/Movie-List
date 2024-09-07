package com.amaterisa.movielistapp.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.databinding.ItemMovieSearchBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.utils.MovieUtils.getImageUrl
import com.bumptech.glide.Glide

class SearchMovieAdapter(
    private val onItemClick: (Long) -> Unit
) : RecyclerView.Adapter<SearchMovieAdapter.MovieSearchViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    class MovieSearchViewHolder(private val binding: ItemMovieSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            onItemClick: (Long) -> Unit
        ) {
            binding.run {
                val url = getImageUrl(300, movie.posterPath)

                txvTitle.text = movie.title
                txvDescription.text = movie.overview

                Glide.with(root.context)
                    .load(url)
                    .into(movieImageView)

                root.setOnClickListener { onItemClick(movie.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        val binding =
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.bind(movieList[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }
}