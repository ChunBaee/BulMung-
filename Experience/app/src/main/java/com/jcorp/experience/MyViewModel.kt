package com.jcorp.experience

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.core.Repo
import com.jcorp.experience.livedata.WeatherLiveData
import com.jcorp.experience.model.LocationItem
import com.jcorp.experience.model.WeatherInfoItem
import java.util.*
import kotlin.math.roundToInt


class MyViewModel : ViewModel(){

    private val _mTimeMills = MutableLiveData<Long>()
    private val mTimeMills : LiveData<Long> = _mTimeMills

    private val _curTime = MutableLiveData<Date>()
    val curTime : LiveData<Date> = _curTime

    private val _curLocation = MutableLiveData<LocationItem>()
    val curLocation : LiveData<LocationItem> = _curLocation



    fun observeTimeMills() {
        _mTimeMills.value = System.currentTimeMillis()
        observeCurTime(Date(mTimeMills.value!!))
    }

    private fun observeCurTime(curTime : Date) {
        _curTime.value = curTime
    }

    fun getCurLocation (nx : Double, ny : Double) {
        _curLocation.value = LocationItem(nx.toString(), ny.toString())
    }
}