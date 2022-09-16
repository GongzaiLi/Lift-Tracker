package com.seng440.jeh128.seng440assignment2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Teal700,
    primaryVariant = Teal500,
    secondary = Teal200,
    secondaryVariant = Teal500,
    surface = Black900,
    onSurface = White000,
    background = Black800,
    onPrimary = Black900,
    onBackground = White000,
    onSecondary = White000,
)

private val LightColorPalette = lightColors(
    primary = Green500,
    primaryVariant = Green200,
    secondary = Teal200,
    secondaryVariant = Teal500,
    surface = White000,
    onSurface = Black900,
    background = Green50,
    onPrimary = White000,
    onBackground = Black900,
    onSecondary = Black900,
)

@Composable
fun Seng440Assignment2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}