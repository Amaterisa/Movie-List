package com.amaterisa.movielistapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amaterisa.movielistapp.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Long): Flow<MovieEntity?>

    @Query("SELECT * FROM movies WHERE isInWatchList = 1")
    fun getWatchListMovies(): Flow<List<MovieEntity>?>

    @Query("UPDATE movies SET isInWatchList = :marked WHERE id = :id")
    suspend fun updateMovieWatchList(id: Long, marked: Boolean)

    @Query("UPDATE movies SET markWatched = :marked WHERE id = :id")
    suspend fun updateMarkedStatus(id: Long, marked: Boolean)
}