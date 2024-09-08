package com.amaterisa.movielistapp.di

import com.amaterisa.movielistapp.domain.repository.MovieRepository
import com.amaterisa.movielistapp.domain.usecase.GetGenresUseCase
import com.amaterisa.movielistapp.domain.usecase.GetMoviesByGenreUseCase
import com.amaterisa.movielistapp.domain.usecase.GetPopularMoviesUseCase
import com.amaterisa.movielistapp.domain.usecase.GetWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.MarkWatchListMovieUseCase
import com.amaterisa.movielistapp.domain.usecase.RemoveMovieFromWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SaveMovieToWatchListUseCase
import com.amaterisa.movielistapp.domain.usecase.SearchMovieUseCase
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
        GetGenresUseCase(movieRepository)

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

    @Singleton
    @Provides
    fun provideSearchMovieUseCase(movieRepository: MovieRepository) =
        SearchMovieUseCase(movieRepository)

}