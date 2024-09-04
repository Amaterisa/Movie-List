package com.amaterisa.movielistapp.di

import com.amaterisa.movielistapp.data.source.remote.MovieApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val AUTH_HEADER = "Authorization"
    const val authToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1Njc0MjFmZWZmYmJmY2IzNmZjNDk3MWQ1ODYzN2U1ZSIsIm5iZiI6MTcyNTM5OTMxMi40NzM4NTUsInN1YiI6IjY2ZDc3ZDJiYjA1NTM2YWI2OWI4OGU1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4sjzYREfPm-PrdtmZaLuwFU5q_wqSHO8EZpUDwd6kGE"

    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(it.request().newBuilder().addHeader(AUTH_HEADER, "Bearer $authToken").build())
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}