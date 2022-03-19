package com.jcorp.rc_additional_3.retrofit

import com.jcorp.rc_additional_3.mViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {
    @GET("me")
    fun getNaverInfo (
        @Header("Authorization") AccessToken : String?,
    ) : Call<NaverData>
}