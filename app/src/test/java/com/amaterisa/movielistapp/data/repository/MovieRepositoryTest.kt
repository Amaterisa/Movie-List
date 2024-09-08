package com.amaterisa.movielistapp.data.repository

import com.amaterisa.movielistapp.data.mapper.GenreMapper
import com.amaterisa.movielistapp.data.mapper.MovieMapper
import com.amaterisa.movielistapp.data.source.local.FakeGenreDao
import com.amaterisa.movielistapp.data.source.local.FakeMovieDao
import com.amaterisa.movielistapp.data.source.remote.MovieApiService
import com.amaterisa.movielistapp.data.source.remote.genre.GenreListResponse
import com.amaterisa.movielistapp.data.source.remote.genre.GenreResponse
import com.amaterisa.movielistapp.data.source.remote.movie.MovieListResponse
import com.amaterisa.movielistapp.data.source.remote.movie.MovieResponse
import com.amaterisa.movielistapp.domain.common.Resource
import com.amaterisa.movielistapp.domain.model.Genre
import com.amaterisa.movielistapp.domain.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieRepositoryTest {
    private var api = mockk<MovieApiService>()
    private val movieDao = FakeMovieDao()
    private val genreDao = FakeGenreDao()

    private val repository = MovieRepositoryImpl(api, movieDao, genreDao)

    private val movie =
        Movie(1, "title", "description", "poster", "backdrop", "date", "vote", listOf(1, 13))
    private val movie2 =
        Movie(2, "title2", "description", "poster", "backdrop", "date", "vote", listOf(1, 13))
    private val movieResponse =
        MovieResponse(
            1,
            "title",
            "description",
            "poster",
            "backdrop",
            "date",
            "vote",
            listOf(1, 13)
        )
    private val popularMoviesResponse =
        MovieListResponse(page = 1, results = listOf(movieResponse))

    private val genre = Genre(id = 1, name = "Drama")
    private val genre2 = Genre(id = 2, name = "Action")

    @Test
    fun `getPopularMovies should emit Loading then Success when api call is success`() =
        runBlocking {
            val mockMovies = listOf(movie)
            coEvery { api.getPopularMovies() } returns popularMoviesResponse
            val flow = repository.getPopularMovies()

            val result = flow.first()
            assertEquals(Resource.Loading, result)

            val secondEmission = flow.drop(1).first()
            assert(secondEmission is Resource.Success)
            assertEquals(mockMovies, (secondEmission as Resource.Success).data)
        }

    @Test
    fun `Should return error when api call fails`(): Unit = runBlocking {
        val throwable = Exception()
        coEvery { api.getPopularMovies() } throws throwable

        val result = repository.getPopularMovies().last()
        Assert.assertEquals(result is Resource.Error, true)
    }

    @Test
    fun `saveMovieToWatchList should update movie in DB if it exists`() = runBlocking {
        val movieEntity = MovieMapper.getMovieEntityFromMovie(movie)
        movieDao.insert(movieEntity)
        repository.saveMovieToWatchList(movie)

        val updatedMovie = movieDao.getMovie(1).first()

        assertNotNull(updatedMovie)
        assertTrue(updatedMovie?.isInWatchList == true)
    }

    @Test
    fun `saveMovieToWatchList should insert new movie if not in DB`() = runBlocking {
        repository.saveMovieToWatchList(movie2)

        val insertedMovie = movieDao.getMovie(2).first()
        assertNotNull(insertedMovie)
        assertTrue(insertedMovie?.isInWatchList == true)
    }

    @Test
    fun `getWatchList should return only movies in watchlist`() = runBlocking {
        val movieEntity1 = MovieMapper.getMovieEntityFromMovie(movie.copy(isInWatchList = true))
        val movieEntity2 = MovieMapper.getMovieEntityFromMovie(movie2.copy(isInWatchList = false))
        movieDao.insertAll(listOf(movieEntity1, movieEntity2))

        val watchlistMovies = repository.getWatchList().first()

        assertEquals(1, watchlistMovies.size)
        assertEquals(movie.title, watchlistMovies.first().title)
    }

    @Test
    fun `removeMovieFromWatchList should update movie isInWatchList attribute to false`() = runBlocking {
        val movieEntity = MovieMapper.getMovieEntityFromMovie(movie.copy(isInWatchList = true))
        movieDao.insert(movieEntity)

        repository.removeMovieFromWatchList(1)

        val updatedMovie = movieDao.getMovie(1).first()
        assertNotNull(updatedMovie)
        assertFalse(updatedMovie?.isInWatchList == true)
    }

    @Test
    fun `getGenreList should emit Loading and then return genres from local database if there is any`() = runBlocking {
        val genreEntity = GenreMapper.getGenreEntityListFromGenre(listOf(genre))
        genreDao.insertAll(genreEntity)

        val flow = repository.getGenreList()

        val result = flow.first()
        assertEquals(Resource.Loading, result)

        val secondEmission = flow.drop(1).first()

        assertTrue(secondEmission is Resource.Success)
        assertEquals(genre.name, (secondEmission as Resource.Success).data.first().name)
    }

    @Test
    fun `getGenreList should fetch from remote if local database is empty`() = runBlocking {
        val mockGenres = GenreListResponse(listOf(GenreResponse(genre.id, genre.name)))
        coEvery { api.getGenres() } returns mockGenres
        genreDao.clear()
        val result = repository.getGenreList().last()

        assertTrue(result is Resource.Success)
        assertEquals(genre.name, (result as Resource.Success).data.first().name)
    }

    @Test
    fun `getGenreList should return genres sorted by name`() = runBlocking {
        genreDao.clear()
        val genreEntity = GenreMapper.getGenreEntityListFromGenre(listOf(genre, genre2))
        genreDao.insertAll(genreEntity)
        val result = repository.getGenreList().last()

        assertTrue(result is Resource.Success)
        assertEquals(genre2.name, (result as Resource.Success).data.first().name)
    }
}