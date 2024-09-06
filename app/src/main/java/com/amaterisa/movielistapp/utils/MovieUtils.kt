package com.amaterisa.movielistapp.utils

import com.amaterisa.movielistapp.domain.model.Genre

object MovieUtils {
    fun getImageUrl(size: Int, path: String): String {
        val sizeString = size.toString()
        return "https://image.tmdb.org/t/p/w$sizeString$path"
    }

    fun getGenreNames(ids: List<Int>, genres: List<Genre>): String {
        return genres.filter { genre -> genre.id in ids }
            .map { genre -> genre.name }
            .joinToString(", ")
    }
}