package com.seng440.jeh128.seng440assignment2.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.material.ExperimentalMaterialApi
import com.seng440.jeh128.seng440assignment2.presentation.MainActivity
import com.seng440.jeh128.seng440assignment2.presentation.components.createShareIntent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val shareIntent = createShareIntent("Test title", "Test subject", "Test Text")
        shareIntent.flags = FLAG_ACTIVITY_NEW_TASK
        context.startActivity(shareIntent)

    }
}