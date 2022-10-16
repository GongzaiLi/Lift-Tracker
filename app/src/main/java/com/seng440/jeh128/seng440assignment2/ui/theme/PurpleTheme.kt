package com.seng440.jeh128.seng440assignment2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val PurpleLightColorPalette = lightColors(
    primary = Color(0xffce93d8),
    primaryVariant = Color(0xFF9c64a6),
    onPrimary = Color.Black,
    secondary = Color(0xFF9575cd),
    secondaryVariant = Color(0xff65499c),
    onSecondary = Color.Black,
    surface = Color(0xFFE7E7E7),
    background = Color(0xFFFFFFFF),
)


private val PurpleDarkColorPalette = darkColors(
    primary = Color(0xff6a1b9a),
    primaryVariant = Color(0xff38006b),
    onPrimary = Color.White,
    secondary = Color(0xff9575cd),
    secondaryVariant = Color(0xff65499c),
    onSecondary = Color.Black,
    surface = Color(0xFF555555),
    background = Color(0xFF1A1A1A),
)


@Composable
fun PurpleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Seng440Assignment2Theme(
        lightColorPalette = PurpleLightColorPalette,
        darkColorPalette = PurpleDarkColorPalette,
        darkTheme = darkTheme,
        content = content
    )
}
