package com.jcorp.e_vap.retrofit

import android.content.ContentValues
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }
    private val iRetrofit : RetrofitApi? = RetrofitClient.getInstance(API.BASE_URL)?.create(RetrofitApi::class.java)

    fun getInfo(pageNo : Int, completion : (API.RESPONSE_STATE, InfoItems) -> Unit) {
        val call = iRetrofit?.chargerInfo(serviceKey = API.serviceKey, pageNo = pageNo, numOfRows = 100, zcode = 27)

        call!!.enqueue(object : retrofit2.Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                Log.d(ContentValues.TAG, "RetrofitManager - onResponse called / Response : ${response.body()!!.body.items.item}")
                completion(API.RESPONSE_STATE.OKAY, response.body()?.body!!.items)
            }

            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "RetrofitManager - onFailure called / t : $t")
            }

        })
    }
}