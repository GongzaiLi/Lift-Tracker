package com.seng440.jeh128.seng440assignment2.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun getIconFromDrawable(iconName: String): Int {
    val context = LocalContext.current
    val drawableId = remember(iconName) {

        context.resources.getIdentifier(
            iconName, "drawable", context.packageName
        )
    }
    return drawableId
}