package com.seng440.jeh128.seng440assignment2.core

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.presentation.MainActivity

@OptIn(ExperimentalMaterialApi::class)
class NotificationService(
    private val context: Context
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(text: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        // todo code change
        val useIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, NotificationReceiver::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_sports_martial_arts_24)
            .setContentTitle("Title here")
            .setContentText("Text here $text")
            .setContentIntent(activityPendingIntent) // can set LED light and number or chat or unread
            .addAction(
                R.drawable.ic_baseline_sports_martial_arts_24,
                "Some Action here",
                useIntent
            )
            .build()


        notificationManager.notify(
            1, // id
            notification
        )
        /*
        .setStyle(
            Notification.BigTextStyle() // some here
        )

         */
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
    }
}