package com.seng440.jeh128.seng440assignment2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.seng440.jeh128.seng440assignment2.ui.theme.attr.Elevations
import com.seng440.jeh128.seng440assignment2.ui.theme.attr.LocalElevations
import com.seng440.jeh128.seng440assignment2.ui.theme.attr.LocalPaddings
import com.seng440.jeh128.seng440assignment2.ui.theme.attr.Paddings

//private val DarkColorPalette = darkColors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200,
//    /*
//    primary = Teal700,
//    primaryVariant = Teal500,
//    secondary = Teal200,
//    secondaryVariant = Teal500,
//    surface = Black900,
//    onSurface = White000,
//    background = Black800,
//    onPrimary = Black900,
//    onBackground = White000,
//    onSecondary = White000,
//     */
//)
//
//private val LightColorPalette = lightColors(
//
//    primary = Purple500,
//    primaryVariant = Purple700,
//    secondary = Teal200,
//    /*
//    primary = Green500,
//    primaryVariant = Green200,
//    secondary = Teal200,
//    secondaryVariant = Teal500,
//    surface = White000,
//    onSurface = Black900,
//    background = Green50,
//    onPrimary = White000,
//    onBackground = Black900,
//    onSecondary = Black900,
//     */
//)

@Composable
fun Seng440Assignment2Theme(
    lightColorPalette: Colors,
    darkColorPalette: Colors,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    CompositionLocalProvider(
        LocalPaddings provides Paddings(),
        LocalElevations provides Elevations(card = 8.dp)
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}

object Seng440Assignment2Theme {
    /**
     * Proxy to [MaterialTheme]
     */
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    /**
     * Proxy to [MaterialTheme]
     */
    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    /**
     * Proxy to [MaterialTheme]
     */
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    /**
     * Retrieves the current [Paddings].
     */
    val paddings: Paddings
        @Composable
        get() = LocalPaddings.current

    /**
     * Retrieves the current [Paddings].
     */
    val elevations: Elevations
        @Composable
        get() = LocalElevations.current

}