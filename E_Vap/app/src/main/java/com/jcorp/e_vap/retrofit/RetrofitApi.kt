package com.jcorp.e_vap.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET(API.getChargerInfo)
    fun chargerInfo(
        @Query("serviceKey") serviceKey: String?,
        @Query("pageNo") pageNo: Int?,
        @Query("numOfRows") numOfRows: Int?,
        @Query("zcode") zcode: Int?
    ): Call<InfoResponse>

}