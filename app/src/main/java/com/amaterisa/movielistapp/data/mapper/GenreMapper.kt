package com.amaterisa.movielistapp.data.mapper

import com.amaterisa.movielistapp.data.source.local.entity.GenreEntity
import com.amaterisa.movielistapp.data.source.remote.genre.GenreListResponse
import com.amaterisa.movielistapp.domain.model.Genre

object GenreMapper {
    fun getGenreListFromResponse(genreListResponse: GenreListResponse): List<Genre> {
        return genreListResponse.genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    fun getGenreListFromEntity(genresEntity: List<GenreEntity>): List<Genre> {
        return genresEntity.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    fun getGenreEntityListFromGenre(genres: List<Genre>): List<GenreEntity> {
        return genres.map {
            GenreEntity(
                id = it.id,
                name = it.name
            )
        }
    }
}