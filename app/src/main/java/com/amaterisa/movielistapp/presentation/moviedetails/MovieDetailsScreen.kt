package com.amaterisa.movielistapp.presentation.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.presentation.utils.MovieUtils.getImageUrl

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onWatchListClick: () -> Unit
) {
    val movie = state.uiModel.movie
    movie?.let {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Image(
                painter = rememberAsyncImagePainter(getImageUrl(500, movie.backdropPath)),
                contentDescription = "Movie image",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = movie.title,
                style = TextStyle(fontSize = 22.sp),
                color = colorResource(R.color.white),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = movie.overview,
                style = TextStyle(fontSize = 12.sp),
                color = colorResource(R.color.gray),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            WatchListButton(
                isInWatchList = state.uiModel.isInWatchList,
                Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                onClick = onWatchListClick
            )

            MovieDetailsCard(
                releaseDate = movie.releaseDate,
                score = movie.voteAverage.toFloat(),
                genres = state.uiModel.genres
            )
        }
    }
}

@Composable
fun WatchListButton(isInWatchList: Boolean, modifier: Modifier, onClick: () -> Unit) {
    if (isInWatchList) {
        RemoveFromWatchListButton(modifier, onClick)
    } else {
        AddToWatchListButton(modifier, onClick)
    }
}

@Composable
fun AddToWatchListButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .border(
                width = 2.dp,
                color = colorResource(R.color.gray),
                shape = RoundedCornerShape(24.dp)
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.transparent),
            contentColor = colorResource(R.color.font_color)
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Add",
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Text(text = "Add to Watchlist")
    }
}

@Composable
fun RemoveFromWatchListButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.cherry_dark),
            contentColor = colorResource(R.color.dark_gray)
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_list_selected),
            contentDescription = "Remove",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(text = "Added to Watchlist")
    }
}


@Composable
fun MovieDetailsCard(releaseDate: String, score: Float, genres: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.dark_gray))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Movie Details",
                color = colorResource(R.color.font_color),
                style = TextStyle(fontSize = 16.sp)
            )
            Row(modifier = Modifier.padding(top = 12.dp)) {
                Column {
                    Text(
                        text = "Release Date: ",
                        color = colorResource(R.color.gray),
                        style = TextStyle(fontSize = 14.sp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Score: ",
                        color = colorResource(R.color.gray),
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Genres: ",
                        color = colorResource(R.color.gray),
                        style = TextStyle(fontSize = 14.sp)
                    )
                }

                Column(modifier = Modifier.padding(start = 24.dp)) {
                    Text(
                        text = releaseDate,
                        color = colorResource(R.color.font_color),
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$score/10",
                        color = colorResource(R.color.font_color),
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = genres,
                        color = colorResource(R.color.font_color),
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}