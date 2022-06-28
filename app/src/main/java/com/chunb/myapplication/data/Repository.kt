package com.chunb.myapplication.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chunb.myapplication.util.RetrofitInterface
import com.chunb.myapplication.util.RetrofitObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val mRetrofitInterface = RetrofitObject.iRetrofit.create(RetrofitInterface::class.java)
    private val kakaoRetrofitInterface = RetrofitObject.kakaoRetrofit.create(RetrofitInterface::class.java)

    fun getListCampingData(data: MutableLiveData<MutableList<Item>>) {
        mRetrofitInterface.getCampingListData(10,"AND", "BulMung", RetrofitObject.serviceKey, "json").enqueue(object : Callback<CampingInfoData> {
            override fun onResponse(
                call: Call<CampingInfoData>,
                response: Response<CampingInfoData>
            ) {
                Log.d("----", "onResponse: ${response.body()!!.response.body.items.item}")
                data.value = response.body()!!.response.body.items.item
            }

            override fun onFailure(call: Call<CampingInfoData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.message}")
            }

        })
    }

    fun getLocationBasedCampingData(data : MutableLiveData<MutableList<Item>>, lat : Double, lng : Double, radius : Int) {
        mRetrofitInterface.getLocationBaseCampingListData(10, "AND", "BulMung", RetrofitObject.serviceKey, lng, lat, radius, "json").enqueue(object : Callback<CampingInfoData> {
            override fun onResponse(call: Call<CampingInfoData>, response: Response<CampingInfoData>) {
                data.value = response.body()!!.response.body.items.item
                Log.d("----", "onResponse: ${response.body()!!.response.body.items.item}")
            }

            override fun onFailure(call: Call<CampingInfoData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.message}")
            }

        })
    }

    fun getSearchBasedCampingData(data : MutableLiveData<MutableList<Item>>, keyword : String) {
        mRetrofitInterface.getSearchBaseCampingListData(100, "AND", "BulMung", RetrofitObject.serviceKey, keyword, "json").enqueue(object : Callback<CampingInfoData> {
            override fun onResponse(call: Call<CampingInfoData>, response: Response<CampingInfoData>) {
                data.value = response.body()!!.response.body.items.item
            }
            override fun onFailure(call: Call<CampingInfoData>, t: Throwable) {
            }

        })
    }

    fun getKakaoLocationSearchData(data : MutableLiveData<MutableList<Place>>, keyword : String) {
        kakaoRetrofitInterface.getLocationBySearch(RetrofitObject.kakaoKey, keyword).enqueue(object : Callback<KakaoLocationSearchData> {
            override fun onResponse(
                call: Call<KakaoLocationSearchData>,
                response: Response<KakaoLocationSearchData>
            ) {
                data.value = response.body()!!.documents as MutableList<Place>
            }
            override fun onFailure(call: Call<KakaoLocationSearchData>, t: Throwable) {
            }
        })
    }

    fun getCampingImageListData(data : MutableLiveData<MutableList<MItem>>, contentId : Int) {
        mRetrofitInterface.getCampingImageListData("AND", "BulMung", RetrofitObject.serviceKey, contentId, "json").enqueue(object : Callback<CampingDetailImageData> {
            override fun onResponse(
                call: Call<CampingDetailImageData>,
                response: Response<CampingDetailImageData>
            ) {
                data.value = response.body()!!.response.body.items.item as MutableList<MItem>
            }
            override fun onFailure(call: Call<CampingDetailImageData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.message}")
            }

        })
    }
}