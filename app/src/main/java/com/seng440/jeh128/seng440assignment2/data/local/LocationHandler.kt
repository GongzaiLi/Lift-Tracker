package com.seng440.jeh128.seng440assignment2.data.local

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject


class LocationHandler @Inject constructor(private val context: Context) {

    // Typically use one cancellation source per lifecycle.
    private val cancellationSource = CancellationTokenSource();

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            context
        )
    }

    private val currentLocationChannel = Channel<Pair<Double, Double>>()

    private val fusedLocationOnSuccessListener = OnSuccessListener<Location> {
        if (it != null) {
            currentLocationChannel.trySend(Pair(it.latitude, it.longitude))
        }
    }

    suspend fun getCurrentLocation(): Pair<Double, Double> {
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationSource.token)
            .addOnSuccessListener(fusedLocationOnSuccessListener)
        return currentLocationChannel.receive()
    }
}