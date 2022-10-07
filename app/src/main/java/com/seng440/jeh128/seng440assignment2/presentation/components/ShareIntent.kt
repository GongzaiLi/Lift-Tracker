package com.seng440.jeh128.seng440assignment2.presentation.components

import android.content.Intent


fun createShareIntent(title: String, subject: String, text: String): Intent {

    val shareIntent =  Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
        return Intent.createChooser(shareIntent, title) // title
}
