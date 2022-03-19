package com.jcorp.rc_additional_1.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }
    private val iRetrofit : RetrofitApi? = RetrofitClient.getInstance(API.BASE_URL)?.create(RetrofitApi::class.java)

    fun getGeo (query : String, completion : (GeoData?) -> Unit) {
        val call = iRetrofit?.getGeoInfo(query = query, ApiID = API.CLIENT_KEY, ApiSecret = API.CLIENT_SECRET)

        call!!.enqueue(object : retrofit2.Callback<GeoData> {
            override fun onResponse(call: Call<GeoData>, response: Response<GeoData>) {
                completion(response.body())
            }

            override fun onFailure(call: Call<GeoData>, t: Throwable) {
                Log.d("----", "onFailure: $t")
            }

        })
    }

    fun getRevGeo (coords : String, completion : (RevGeoData?) -> Unit) {
        val call = iRetrofit?.getRevGeoInfo(coords = coords, output = "json", orders = "addr",ApiID = API.CLIENT_KEY, ApiSecret = API.CLIENT_SECRET)
        call!!.enqueue(object : retrofit2.Callback<RevGeoData> {
            override fun onResponse(call: Call<RevGeoData>, response: Response<RevGeoData>) {
                completion(response.body())
            }
            override fun onFailure(call: Call<RevGeoData>, t: Throwable) {
                Log.d("----", "onFailure: $t")
            }

        })
    }

}