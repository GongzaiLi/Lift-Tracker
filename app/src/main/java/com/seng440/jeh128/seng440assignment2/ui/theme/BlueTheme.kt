package com.seng440.jeh128.seng440assignment2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BlueLightColorPalette = lightColors(
    primary = Color(0xff64b5f6),
    primaryVariant = Color(0xff2286c3),
    onPrimary = Color.Black,
    secondary = Color(0xFFb3e5fc),
    secondaryVariant = Color(0xff82b3c9),
    onSecondary = Color.Black,
    surface = Color(0xFFE7E7E7),
    background = Color(0xFFFFFFFF),
)

private val BlueDarkColorPalette = darkColors(
    primary = Color(0xff1565c0),
    primaryVariant = Color(0xff003c8f),
    onPrimary = Color.White,
    secondary = Color(0xff2962ff),
    secondaryVariant = Color(0xff0039cb),
    onSecondary = Color.Black,
    surface = Color(0xFF555555),
    background = Color(0xFF1A1A1A),
)

@Composable
fun BlueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Seng440Assignment2Theme(
        lightColorPalette = BlueLightColorPalette,
        darkColorPalette = BlueDarkColorPalette,
        darkTheme = darkTheme,
        content = content
    )
}
