package com.amaterisa.movielistapp.di

import com.amaterisa.movielistapp.data.repository.MovieRepositoryImpl
import com.amaterisa.movielistapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}