package com.jcorp.experience

import androidx.lifecycle.LiveData
import com.jcorp.experience.model.WeatherInfoItem

class RepoImpl : Repo {

    override fun getWeatherData(nx: Int, ny: Int): MutableList<WeatherInfoItem> {
        return super.getWeatherData(nx, ny)
    }
}