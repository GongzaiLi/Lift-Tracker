package com.seng440.jeh128.seng440assignment2.presentation.components

import android.content.Intent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShareButton(title: String, subject: String, text: String) {
    val shareIntent = createShareIntent(title, subject, text)
    val context = LocalContext.current

    IconButton(onClick = { context.startActivity(shareIntent) }) {
        Icon(Icons.Default.Share, contentDescription = null)
    }

}