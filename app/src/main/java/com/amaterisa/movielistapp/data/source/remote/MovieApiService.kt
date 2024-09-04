package com.amaterisa.movielistapp.data.source.remote

import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieListResponse
}