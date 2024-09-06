package com.amaterisa.movielistapp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amaterisa.movielistapp.data.source.local.dao.MovieDao
import com.amaterisa.movielistapp.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun watchListMovieDao(): MovieDao
}