package com.jcorp.e_vap.livedata

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.jcorp.e_vap.model.LocationItem

class LocationLiveData (application: Application) : LiveData<LocationItem>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private var mApplication = application

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onActive() {
        super.onActive()
        if (ActivityCompat.checkSelfPermission(
                mApplication,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mApplication,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            location : Location -> location.also {
                setLocationData(it)
            }
        }
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                mApplication,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mApplication,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult ?: return

            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = LocationItem(location.longitude.toString(), location.latitude.toString())
    }

    companion object {
        val ONE_MIN : Long = 60000
        val FASTEST : Long = 1000
        val locationRequest : LocationRequest = LocationRequest.create().apply {
            interval = ONE_MIN
            fastestInterval = FASTEST
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    }

}