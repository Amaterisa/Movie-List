package com.amaterisa.movielistapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amaterisa.movielistapp.data.source.local.entity.GenreEntity
@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<GenreEntity>
}