package com.solie.ev_nyang.firebase

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }
    private val iRetrofit : RetrofitInterface? = RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)

    fun getInfo(pageNo : Int, completion : (API.RESPONSE_STATE, InfoItems) -> Unit) {
        val call = iRetrofit?.chargerInfo(serviceKey = API.serviceKey, pageNo = pageNo, numOfRows = 100, zcode = 27)

        call!!.enqueue(object : retrofit2.Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                Log.d(TAG, "RetrofitManager - onResponse called / Response : ${response.body()!!.body.items.item}")
                completion(API.RESPONSE_STATE.OKAY, response.body()?.body!!.items)
            }

            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure called / t : $t")
            }

        })
    }

    fun getStatus(pageNo : Int, completion : (API.RESPONSE_STATE, StatusItems) -> Unit) {
        val call = iRetrofit?.chargerStatus(serviceKey = API.serviceKey, pageNo = 1, numOfRows = 10, zcode = 11, period = 1)
        call!!.enqueue(object : retrofit2.Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                Log.d(TAG, "onResponse: refresh Success")
                response.body()?.body?.items?.let { completion(API.RESPONSE_STATE.OKAY, it) }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: refresh Failed / t : $t")
            }

        })
    }
}