package com.amaterisa.movielistapp.presentation.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.utils.MovieUtils.getImageUrl

@Composable
fun MovieItem(movie: Movie, onClick: ((Movie) -> Unit)?) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick?.invoke(movie) },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(getImageUrl(300, movie.posterPath)),
                contentDescription = movie.title,
                modifier = Modifier.size(
                    width = dimensionResource(id = R.dimen.top_movie_width),
                    height = dimensionResource(id = R.dimen.top_movie_height)
                ),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MaterialTheme {
        val movie = Movie(
            1,
            "Title",
            "overview",
            "path/to/movie/poster",
            backdropPath = "path/to/movie/poster",
            releaseDate = "date",
            voteAverage = "5",
            genreIds = listOf()
        )
        MovieItem(movie, null)
    }
}