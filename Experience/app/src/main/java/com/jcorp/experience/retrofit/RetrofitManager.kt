package com.jcorp.experience.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import com.jcorp.experience.model.WeatherItem
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }
    private val iRetrofit : RetrofitAPI? = RetrofitClient.getInstance(API.Weather_URL)?.create(RetrofitAPI::class.java)

    fun getWeather(nx : Int, ny : Int, base_date : Int, base_time : Int, completion : (API.RESPONSE_STATE, WeatherItems) -> Unit) {
        val call = iRetrofit?.WeatherInfo(serviceKey = API.service_Key, numOfRows = 100, pageNo = 1, dataType = "XML", base_date = base_date, base_time = base_time, nx = nx, ny = ny)

        call!!.enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                completion(API.RESPONSE_STATE.OKAY, response.body()?.body!!.items)
                Log.d(TAG, "onResponse: //SUCCESS")
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ERROR is $t")
            }

        })

    }
}