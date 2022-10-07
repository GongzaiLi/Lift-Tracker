package com.seng440.jeh128.seng440assignment2.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ShareCompat
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.presentation.MainActivity
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
class NotificationService(
    private val context: Context
) {
    private val notificationId = 0
    private val resources = context.resources

    // Create an explicit intent for an Activity in your app
    private val contentIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    private val contentPendingIntent: PendingIntent =
        PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE)



    private fun getSharePendingIntent(exerciseName: String, weight: String, location: String): PendingIntent {
        val extraText = resources.getString(
            R.string.personal_best_share_text, exerciseName,
            weight, location
        )

        val shareIntent = Intent().apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.myPersonalbest))
            putExtra(Intent.EXTRA_TEXT, extraText)
            type = "text/plain"
        }

        return PendingIntent.getActivity(context, Random.nextInt(), shareIntent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun getNotificationBuilder(sharePendingIntent: PendingIntent, exerciseName: String, weight: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.muscle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(resources.getString(R.string.notification_title))
            .setContentText(resources.getString(R.string.notification_description, weight, exerciseName))
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .addAction(1, resources.getString(R.string.shareButtonText), sharePendingIntent)
    }


    fun showNotification(exerciseName: String, weight: String, location: String) {
        val sharePendingIntent = getSharePendingIntent(exerciseName, weight, location)
        val notificationBuilder = getNotificationBuilder(sharePendingIntent, exerciseName, weight)

        with(NotificationManagerCompat.from(context))
        {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, notificationBuilder.build())
        }
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
    }
}