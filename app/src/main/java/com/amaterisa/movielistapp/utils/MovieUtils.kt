package com.amaterisa.movielistapp.utils

import android.widget.ImageView
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.domain.model.Genre
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object MovieUtils {
    fun getImageUrl(size: Int, path: String): String {
        val sizeString = size.toString()
        return "https://image.tmdb.org/t/p/w$sizeString$path"
    }

    fun getGenreNames(ids: List<Int>, genres: List<Genre>): String {
        return genres.filter { genre -> genre.id in ids }.joinToString(", ") { genre -> genre.name }
    }

    fun loadImageWithGlide(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .error(R.drawable.img_error)
            )
            .into(imageView)
    }
}