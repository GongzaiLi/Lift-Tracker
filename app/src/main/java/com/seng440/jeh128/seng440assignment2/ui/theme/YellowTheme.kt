package com.seng440.jeh128.seng440assignment2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val YellowLightColorPalette = lightColors(
    primary = Color(0xffffee58),
    primaryVariant = Color(0xFF8D8D31),
    onPrimary = Color.Black,
    secondary = Color(0xFFD4E157),
    secondaryVariant = Color(0xffa0af22),
    onSecondary = Color.Black,
    surface = Color(0xFFE7E7E7),
    background = Color(0xFFFFFFFF),
)

private val YellowDarkColorPalette = darkColors(
    primary = Color(0xfff9a825),
    primaryVariant = Color(0xffc17900),
    onPrimary = Color.Black,
    secondary = Color(0xffffd600),
    secondaryVariant = Color(0xffc7a500),
    onSecondary = Color.Black,
    surface = Color(0xFF555555),
    background = Color(0xFF1A1A1A),
)


@Composable
fun YellowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Seng440Assignment2Theme(
        lightColorPalette = YellowLightColorPalette,
        darkColorPalette = YellowDarkColorPalette,
        darkTheme = darkTheme,
        content = content
    )
}
