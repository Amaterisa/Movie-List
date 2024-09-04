package com.amaterisa.movielistapp.presentation.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.databinding.ItemMovieBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MovieListAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onWatchlistClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    // ViewHolder class to hold the item views
    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onItemClick: (Movie) -> Unit, onWatchlistClick: (Movie) -> Unit) {

            // Load the image using an image loading library (e.g., Glide)
            val url = "https://image.tmdb.org/t/p/w300${movie.posterPath}"

            Log.d("TAG", "url $url")


            Glide.with(binding.root.context)
                .load(url)
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("Glide", "Image loaded successfully")
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Image load failed", e)
                        return false
                    }
                })
                .into(binding.movieImageView)

            // Set click listener for the entire item
            binding.root.setOnClickListener { onItemClick(movie) }

            // Set click listener for the watchlist button
            //binding.buttonAddToWatchlist.setOnClickListener { onWatchlistClick(movie) }
        }
    }

    // Inflate the layout for each item and create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    // Bind the data to the views
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], onItemClick, onWatchlistClick)
    }

    // Return the total number of items
    override fun getItemCount(): Int = movieList.size

    fun setMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
    }
}
