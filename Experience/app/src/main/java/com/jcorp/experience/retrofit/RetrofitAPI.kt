package com.jcorp.experience.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET(API.Weather_APi)
    fun WeatherInfo(
        @Query("serviceKey") serviceKey : String?,
        @Query("numOfRows") numOfRows : Int?,
        @Query("pageNo") pageNo : Int?,
        @Query("dataType") dataType : String?,
        @Query("base_date") base_date : Int?,
        @Query("base_time") base_time : Int?,
        @Query("nx") nx : Int?,
        @Query("ny") ny : Int?
    ) : Call<WeatherResponse>
}