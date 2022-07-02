package com.chunb.myapplication.util

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private const val BASE_URL = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/"
    val serviceKey = "mS+Jm4ryE9IQLhsedrzyXNRDQi3aqCSUuFRAMFx6hW+IVn7tnoEU97t8qwrpVyrJVp9RrZPFld+9kYbIGuw7rw=="

    private const val KAKAO_URL = "https://dapi.kakao.com/"
    val kakaoKey = "KakaoAK e2b14ed353b0a1f0cca48f258cfb6551"

    val iRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    val kakaoRetrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(KAKAO_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}