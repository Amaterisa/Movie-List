package com.amaterisa.movielistapp.di

import android.content.Context
import androidx.room.Room
import com.amaterisa.movielistapp.data.source.local.dao.WatchListMovieDao
import com.amaterisa.movielistapp.data.source.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(appContext, MovieDatabase::class.java, "movies.db").build()
    }

    @Provides
    fun provideWatchListMovieDao(database: MovieDatabase): WatchListMovieDao {
        return database.watchListMovieDao()
    }
}