package com.solie.ev_nyang.firebase

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET(API.getChargerInfo)
    fun chargerInfo(
        @Query("serviceKey") serviceKey: String?,
        @Query("pageNo") pageNo: Int?,
        @Query("numOfRows") numOfRows: Int?,
        @Query("zcode") zcode: Int?
    ): Call<InfoResponse>


    @GET(API.getChargerStatus)
    fun chargerStatus(
        @Query("serviceKey") serviceKey: String?,
    @Query("pageNo") pageNo: Int?,
    @Query("numOfRows") numOfRows: Int?,
    @Query("period") period : Int?,
    @Query("zcode") zcode: Int?) : Call<StatusResponse>
}