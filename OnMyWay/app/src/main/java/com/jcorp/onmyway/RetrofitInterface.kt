package com.jcorp.onmyway

import com.jcorp.onmyway.data.BaseData
import com.jcorp.onmyway.data.JsonData
import com.jcorp.onmyway.data.PostData
import com.jcorp.onmyway.data.Result
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @GET("/test")
    fun getData() : Call<JsonData>

    @PUT("/test/11")
    fun putData(
        @Body parameters : Result
    ) : Call<PostData>
}