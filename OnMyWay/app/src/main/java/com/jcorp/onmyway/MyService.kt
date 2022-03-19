package com.jcorp.onmyway

import android.util.Log
import com.jcorp.onmyway.data.JsonData
import com.jcorp.onmyway.data.PostData
import com.jcorp.onmyway.data.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyService(val view : MainActivityView) {
    private val mRetrofitInterface = ApplicationClass.mRetrofit.create(RetrofitInterface::class.java)
    fun tryGetData() {
        mRetrofitInterface.getData().enqueue(object : Callback<JsonData> {
            override fun onResponse(call: Call<JsonData>, response: Response<JsonData>) {
                view.onGetDataSuccess(response.body() as JsonData)
            }

            override fun onFailure(call: Call<JsonData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun tryPostData(name : String) {
        mRetrofitInterface.putData(Result(name, 5, "2022-03-17 00:00:00")).enqueue(object : Callback<PostData> {
            override fun onResponse(call: Call<PostData>, response: Response<PostData>) {
                view.onPostDataSuccess(response.body()?.result as Result)
            }

            override fun onFailure(call: Call<PostData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.message.toString()}, ${call.request().body}")
            }

        })
    }
}