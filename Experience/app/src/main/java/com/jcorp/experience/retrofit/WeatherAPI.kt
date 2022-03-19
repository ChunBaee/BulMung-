package com.jcorp.experience.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object WeatherAPI {

    fun getWeather (nx : Int, ny : Int, baseTime : Int, baseDate : Int){
        RetrofitManager.instance.getWeather(
            nx = nx,
            ny = ny,
            base_time = baseTime,
            base_date = baseDate,
            completion = { responseState, weatherItems ->
                when (responseState) {
                    API.RESPONSE_STATE.OKAY -> {
                        Log.d(TAG, "getWeather: API 호출 성공 //")
                        upLoadToDatabase(weatherItems)
                    }
                }

            })
    }

    private fun upLoadToDatabase (response : WeatherItems) {
        val weatherMap = mutableMapOf<String, Any>()
        val baseMap = mutableMapOf<String, Any>()
        for(item in response.item) {
            weatherMap[item.category.toString()] = item.fcstValue.toString()
            baseMap["BaseTime"] = item.baseTime.toString()

            FirebaseFirestore.getInstance().collection("WeatherDB")
                .document("${item.nx} _ ${item.ny}")
                .set(baseMap)

            FirebaseFirestore.getInstance().collection("WeatherDB")
                .document("${item.nx} _ ${item.ny}")
                .collection("${item.baseDate}")
                .document(item.fcstTime.toString())
                .set(weatherMap)

        }

    }

}