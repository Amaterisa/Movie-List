package com.amaterisa.movielistapp.utils

object MovieUtils {
    fun getImageUrl(size: Int, path: String): String {
        val sizeString = size.toString()
        return "https://image.tmdb.org/t/p/w$sizeString$path"
    }
}