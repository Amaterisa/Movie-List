package com.amaterisa.movielistapp

import androidx.compose.material3.*  // Import Material3 Theme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Black = Color(0xFF000000)
val CherryLight = Color(0xFFC9184A)
val FontColor = Color(0xFFF7F7F7)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        color = FontColor
    )
)

private val LightThemeColors = lightColorScheme(
    primary = Black,
    secondary = CherryLight,
    surface = Black,
    onSurface = FontColor
)

private val DarkThemeColors = darkColorScheme(
    primary = Black,
    secondary = CherryLight,
    surface = Black,
    onSurface = FontColor
)

@Composable
fun MovieListAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography,
        content = content
    )
}