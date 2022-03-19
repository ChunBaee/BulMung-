package com.jcorp.rc_additional_1.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance : Retrofit? = null
    fun getInstance (baseUrl : String) : Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}