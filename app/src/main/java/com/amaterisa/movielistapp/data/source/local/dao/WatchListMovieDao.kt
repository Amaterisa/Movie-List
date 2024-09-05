package com.amaterisa.movielistapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.amaterisa.movielistapp.data.source.local.entity.WatchListMovieEntity
import androidx.room.Query

@Dao
interface WatchListMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: WatchListMovieEntity)

    @Query("SELECT * FROM movies_watch_list")
    suspend fun getAllMovies(): List<WatchListMovieEntity>

    @Query("SELECT * FROM movies_watch_list WHERE id = :id")
    suspend fun getMovie(id: Long): WatchListMovieEntity?

    @Delete
    suspend fun delete(movie: WatchListMovieEntity)

    @Query("DELETE FROM movies_watch_list WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE movies_watch_list SET markWatched = :marked WHERE id = :id")
    suspend fun updateMarkedStatus(id: Long, marked: Boolean)
}