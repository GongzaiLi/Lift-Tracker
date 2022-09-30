package com.seng440.jeh128.seng440assignment2.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = NotificationService(context)
        service.showNotification("Insert Text here")
    }
}