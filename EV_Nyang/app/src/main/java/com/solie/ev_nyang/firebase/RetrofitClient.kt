package com.solie.ev_nyang.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit


object RetrofitClient {
   private var retrofitClient : Retrofit? = null

    fun getClient (baseUrl : String) : Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() Called")

        if(retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .build()
        }
        return retrofitClient
    }
}