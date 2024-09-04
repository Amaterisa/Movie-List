package com.amaterisa.movielistapp.data.source.remote

import com.amaterisa.movielistapp.data.source.remote.genre.GenreListResponse
import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieListResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreListResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: Long): MovieListResponse
}