package com.jcorp.e_vap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jcorp.e_vap.livedata.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData
}