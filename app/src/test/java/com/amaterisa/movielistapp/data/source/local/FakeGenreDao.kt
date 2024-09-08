package com.amaterisa.movielistapp.data.source.local

import com.amaterisa.movielistapp.data.source.local.dao.GenreDao
import com.amaterisa.movielistapp.data.source.local.entity.GenreEntity

class FakeGenreDao: GenreDao {
    private val genreList: MutableList<GenreEntity> = mutableListOf()

    override suspend fun insertAll(genres: List<GenreEntity>) {
        genreList.addAll(genres)
    }

    override suspend fun getAll(): List<GenreEntity> {
        return genreList
    }

    fun clear() {
        genreList.clear()
    }
}