package com.jcorp.rc_additional_3.retrofit

import android.util.Log
import com.jcorp.rc_additional_3.LoginActivity
import com.jcorp.rc_additional_3.mViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RetrofitManager() {
    companion object {
        val instance = RetrofitManager()
    }
    private val naverRetrofit : RetrofitApi? = RetrofitClient.getInstance(API.NAVER_URL)?.create(RetrofitApi::class.java)

    fun getNaverData (token : String, completion : (NaverData?) -> Unit) {
        val call = naverRetrofit?.getNaverInfo(token)
        call!!.enqueue(object : Callback<NaverData>{
            override fun onResponse(call: Call<NaverData>, response: Response<NaverData>) {
                completion(response.body())
            }

            override fun onFailure(call: Call<NaverData>, t: Throwable) {
                Log.d("----", "onFailure: $t")
            }

        })
    }
}