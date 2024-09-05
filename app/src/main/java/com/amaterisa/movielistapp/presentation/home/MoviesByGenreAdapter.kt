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
    private val onItemClick: (Movie) -> Unit,
    private val onWatchlistClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesByGenreAdapter.MoviesByGenreViewHolder>() {
    private var moviesByGenre: MutableMap<String, MutableList<Movie>> = mutableMapOf()
    private var genres: MutableList<String> = mutableListOf()

    class MoviesByGenreViewHolder(private val binding: ItemHomeMovieByGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var adapter: MovieListAdapter? = null

        init {
            val spaceInPixels =
                binding.root.context.resources.getDimensionPixelSize(R.dimen.a_quarter_default_padding)
            binding.moviesRv.addItemDecoration(LinearItemDecoration(spaceInPixels, false))
        }

        fun bind(
            genre: String,
            movies: List<Movie>,
            imageWidth: Int,
            imageHeight: Int,
            onItemClick: (Movie) -> Unit,
            onWatchlistClick: (Movie) -> Unit,
        ) {
            binding.run {
                txvGenre.text = genre
                adapter =
                    MovieListAdapter(imageWidth, imageHeight, onItemClick, onWatchlistClick)
                adapter?.setMovies(movies)
                binding.moviesRv.adapter = adapter

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesByGenreViewHolder {
        val binding =
            ItemHomeMovieByGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesByGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesByGenreViewHolder, position: Int) {
        val genre = genres[position]
        val movies = moviesByGenre[genre] ?: emptyList()
        holder.bind(
            genre,
            movies,
            imageWidth,
            imageHeight,
            onItemClick,
            onWatchlistClick
        )
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    fun setMoviesByGenre(movies: Pair<Genre, List<Movie>>) {
        val genre = movies.first.name
        val isNewGenre = !moviesByGenre.containsKey(genre)

        if (isNewGenre) {
            moviesByGenre[genre] = movies.second.toMutableList()
            genres.add(genre)
            notifyItemInserted(genres.size - 1)
        } else {
            val existingMovies = moviesByGenre[genre]
            existingMovies?.clear()
            existingMovies?.addAll(movies.second)
            notifyItemChanged(genres.indexOf(genre))
        }
    }

    fun addToWatchList(movie: Movie) {
        for ((genre, movies) in moviesByGenre) {
            val index = movies.indexOfFirst { it.id == movie.id }
            if (index != -1) {
                movies[index] = movie
                notifyItemChanged(genres.indexOf(genre))
            }
        }
    }
}