package com.amaterisa.movielistapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ItemHomeMovieByGenreBinding
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.adapter.LinearItemDecoration
import com.amaterisa.movielistapp.presentation.adapter.MovieListAdapter

class MoviesByGenreAdapter(
    private val imageWidth: Int,
    private val imageHeight: Int,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesByGenreAdapter.MoviesByGenreViewHolder>() {
    private var moviesByGenre: MutableMap<Genre, List<Movie>> = mutableMapOf()

    class MoviesByGenreViewHolder(private val binding: ItemHomeMovieByGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var adapter: MovieListAdapter? = null

        fun bind(
            genre: Genre,
            movies: List<Movie>,
            imageWidth: Int,
            imageHeight: Int,
            onItemClick: (Movie) -> Unit
        ) {
            binding.run {
                txvGenre.text = genre.name
                if (binding.moviesRv.adapter == null) {
                    val spaceInPixels =
                        binding.root.context.resources.getDimensionPixelSize(R.dimen.a_quarter_default_padding)
                    binding.moviesRv.addItemDecoration(LinearItemDecoration(spaceInPixels, false))

                    adapter =
                        MovieListAdapter(imageWidth, imageHeight, onItemClick)
                    adapter?.setMovies(movies)
                    binding.moviesRv.adapter = adapter
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesByGenreViewHolder {
        val binding =
            ItemHomeMovieByGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesByGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesByGenreViewHolder, position: Int) {
        val genre = moviesByGenre.keys.elementAt(position) // Get genre by position
        val movies = moviesByGenre[genre] ?: emptyList()
        holder.bind(
            genre,
            movies,
            imageWidth,
            imageHeight,
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return moviesByGenre.keys.size
    }

    fun setMoviesByGenre(movies: Map<Genre, List<Movie>> = emptyMap()) {
        moviesByGenre = movies.toMutableMap()
    }
}