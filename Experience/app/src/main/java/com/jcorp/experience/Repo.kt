package com.jcorp.experience

import androidx.lifecycle.LiveData
import com.jcorp.experience.livedata.WeatherLiveData
import com.jcorp.experience.model.WeatherInfoItem

interface Repo {

    fun getWeatherData(nx : Int, ny : Int) : MutableList<WeatherInfoItem> {
        return WeatherLiveData(nx, ny).value!!
    }

}