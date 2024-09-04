package com.amaterisa.movielistapp.data.source.remote.genre

import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres")val genres: List<GenreResponse>
)