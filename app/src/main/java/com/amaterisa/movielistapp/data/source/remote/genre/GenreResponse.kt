package com.amaterisa.movielistapp.data.source.remote.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)
