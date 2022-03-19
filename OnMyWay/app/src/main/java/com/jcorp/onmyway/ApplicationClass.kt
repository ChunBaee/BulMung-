package com.jcorp.onmyway

import android.app.Application
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    val API_URL = "http://apis.newvement.com"

    companion object {
        lateinit var mSharedPreference : SharedPreferences
        lateinit var mRetrofit : Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        mSharedPreference = applicationContext.getSharedPreferences("Preference", MODE_PRIVATE)
        initRetrofitInstance()
    }

    private fun initRetrofitInstance() {
        val client : OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            //.addNetworkInterceptor() - 자동헤더
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}