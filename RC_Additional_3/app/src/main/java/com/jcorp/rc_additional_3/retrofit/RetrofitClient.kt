package com.jcorp.rc_additional_3.retrofit

import android.util.Log
import com.jcorp.rc_additional_3.MainActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance : Retrofit? = null
    fun getInstance (baseUrl : String) : Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                //.client(provideOkHttpClient(AppInterCeptor(token)))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }

    /*private fun provideOkHttpClient(
        interceptor : AppInterCeptor) : OkHttpClient = OkHttpClient.Builder()
        .run {
            addInterceptor(interceptor)
            build()
        }

    class AppInterCeptor (token : String): Interceptor {
        private val mToken = token
        override fun intercept(chain: Interceptor.Chain): Response = with(chain){
            val newRequest = request().newBuilder()
                .addHeader("Authorization", mToken)
                .build()

            Log.d("----", "intercept: $mToken")
            proceed(newRequest)
        }

    }*/
}