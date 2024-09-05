package com.amaterisa.movielistapp.di

import com.amaterisa.movielistapp.domain.repository.MovieRepository
import com.amaterisa.movielistapp.domain.usecase.GetGenreUseCase
import com.amaterisa.movielistapp.domain.usecase.GetMoviesByGenreUseCase
import com.amaterisa.movielistapp.domain.usecase.GetPopularMoviesUseCase
import com.amaterisa.movielistapp.domain.usecase.GetWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.MarkWatchListMovieUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository) =
        GetPopularMoviesUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideGetGenreUseCase(movieRepository: MovieRepository) =
        GetGenreUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideGetMoviesByGenreUseCase(movieRepository: MovieRepository) =
        GetMoviesByGenreUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideSaveMovieToWatchListUseCase(movieRepository: MovieRepository) =
        SaveMovieToWatchListUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideGetWatchListUseCase(movieRepository: MovieRepository) =
        GetWatchListUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideRemoveMovieFromWatchListUseCase(movieRepository: MovieRepository) =
        RemoveMovieFromWatchListUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideMarkWatchListMovieUseCase(movieRepository: MovieRepository) =
        MarkWatchListMovieUseCase(movieRepository)

}