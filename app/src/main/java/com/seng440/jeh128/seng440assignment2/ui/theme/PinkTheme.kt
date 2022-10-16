package com.seng440.jeh128.seng440assignment2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PinkLightColorPalette  = lightColors(
    primary = Color(0xfff06292),
    primaryVariant = Color(0xFFba2d65),
    onPrimary = Color.Black,
    secondary = Color(0xFFd81b60),
    secondaryVariant = Color(0xffa00037),
    onSecondary = Color.Black,
    surface = Color(0xFFE7E7E7),
    background = Color(0xFFFFFFFF),
)

private val  PinkDarkColorPalette = darkColors(
    primary = Color(0xffad1457),
    primaryVariant = Color(0xff78002e),
    onPrimary = Color.White,
    secondary = Color(0xffd81b60),
    secondaryVariant = Color(0xffa00037),
    onSecondary = Color.Black,
    surface = Color(0xFF555555),
    background = Color(0xFF1A1A1A),
)

@Composable
fun PinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    Seng440Assignment2Theme(
        lightColorPalette = PinkLightColorPalette,
        darkColorPalette = PinkDarkColorPalette,
        darkTheme = darkTheme,
        content = content
    )
}
