package com.seng440.jeh128.seng440assignment2.data.local

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject


class LocationHandler @Inject constructor(private val context: Context) {

    // Typically use one cancellation source per lifecycle.
    private val cancellationSource = CancellationTokenSource() //TODO cancel somewhere

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            context
        )
    }
    private val geCoder by lazy { Geocoder(context) }

    private val currentLocationStringChannel = Channel<String>()
    private val currentLocationChannel = Channel<Location>()


    private val fusedLocationOnSuccessListener = OnSuccessListener<Location> {
        it.let {
            currentLocationChannel.trySend(it)
        }
    }

    private suspend fun getLocation(): Location {
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationSource.token)
            .addOnSuccessListener(fusedLocationOnSuccessListener)
        return currentLocationChannel.receive()
    }

    suspend fun getCurrentLocationString(): String {
        val location = getLocation()
        val addresses = try {
            geCoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (e: Exception) {
            null
        }
        val formattedAddress = addresses?.get(0)?.getAddressLine(0)

        currentLocationStringChannel.trySend(formattedAddress ?: ":(")
        return currentLocationStringChannel.receive()
    }

}