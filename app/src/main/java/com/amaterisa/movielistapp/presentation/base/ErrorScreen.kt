package com.amaterisa.movielistapp.presentation.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amaterisa.movielistapp.R
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.amaterisa.movielistapp.CherryLight
import com.amaterisa.movielistapp.FontColor

@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_error),
            contentDescription = "Error",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = R.string.error_label),
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.white),
            style = TextStyle(fontSize = 22.sp)
        )

        Text(
            text = stringResource(id = R.string.error_description),
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.gray),
            style = TextStyle(fontSize = 14.sp)
        )

        Button(
            onClick = onRetry,
            modifier = Modifier
                .padding(top = 24.dp)
                .widthIn(min = 240.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CherryLight,
                contentColor = FontColor
            ),
        ) {
            Text(
                text = stringResource(id = R.string.retry_label),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.dark_gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorLayoutPreview() {
    MaterialTheme {
        ErrorScreen(
            onRetry = {}
        )
    }
}