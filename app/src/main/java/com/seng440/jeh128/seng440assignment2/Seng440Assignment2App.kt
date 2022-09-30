package com.seng440.jeh128.seng440assignment2

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.seng440.jeh128.seng440assignment2.core.NotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Seng440Assignment2App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.CHANNEL_ID,
                "channel_name",
                NotificationManager.IMPORTANCE_HIGH, // position
            )
            channel.description = "Some description here"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

