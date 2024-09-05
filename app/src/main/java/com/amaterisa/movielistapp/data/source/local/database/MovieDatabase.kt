package com.amaterisa.movielistapp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amaterisa.movielistapp.data.source.local.dao.WatchListMovieDao
import com.amaterisa.movielistapp.data.source.local.entity.WatchListMovieEntity

@Database(entities = [WatchListMovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun watchListMovieDao(): WatchListMovieDao
}