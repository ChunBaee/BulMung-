package com.jcorp.rc_additional_1.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET(API.GEO_URL)
    fun getGeoInfo (
        @Query("query") query : String?,
        @Query("X-NCP-APIGW-API-KEY-ID") ApiID : String?,
        @Query("X-NCP-APIGW-API-KEY") ApiSecret : String?
    ) : Call<GeoData>

    @GET(API.REV_GEO_URL)
    fun getRevGeoInfo (
        @Query("coords") coords : String?,
        @Query("output") output : String?,
        @Query("orders") orders : String?,
        @Query("X-NCP-APIGW-API-KEY-ID") ApiID : String?,
        @Query("X-NCP-APIGW-API-KEY") ApiSecret : String?
    ) : Call<RevGeoData>
}