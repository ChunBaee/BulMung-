package com.chunb.myapplication.util

import com.chunb.myapplication.data.CampingDetailImageData
import com.chunb.myapplication.data.CampingInfoData
import com.chunb.myapplication.data.KakaoLocationSearchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("basedList")
    fun getCampingListData (
        @Query("numOfRows") numOfRows : Int?,
        @Query("MobileOS") MobileOS : String?,
        @Query("MobileApp") MobileApp : String?,
        @Query("ServiceKey") ServiceKey : String?,
        @Query("_type") _type : String?
    ) : Call<CampingInfoData>

    @GET("locationBasedList")
    fun getLocationBaseCampingListData(
        @Query("numOfRows") numOfRows: Int?,
        @Query("MobileOS") MobileOS: String?,
        @Query("MobileApp") MobileApp: String?,
        @Query("ServiceKey") ServiceKey: String?,
        @Query("mapX") mapX : Double?,
        @Query("mapY") mapY : Double?,
        @Query("radius") radius : Int?,
        @Query("_type") _type : String?
    ) : Call<CampingInfoData>

    @GET("searchList")
    fun getSearchBaseCampingListData(
        @Query("numOfRows") numOfRows: Int?,
        @Query("MobileOS") MobileOS: String?,
        @Query("MobileApp") MobileApp: String?,
        @Query("ServiceKey") ServiceKey: String?,
        @Query("keyword") keyword : String?,
        @Query("_type") _type : String?
    ) : Call<CampingInfoData>

    @GET("imageList")
    fun getCampingImageListData (
        @Query("MobileOS") MobileOS: String?,
        @Query("MobileApp") MobileApp: String?,
        @Query("ServiceKey") ServiceKey: String?,
        @Query("contentId") contentId : Int?,
        @Query("_type") _type : String?
    ) : Call<CampingDetailImageData>


    @GET("v2/local/search/keyword.json")
    fun getLocationBySearch(
        @Header("Authorization") key : String?,
        @Query("query") query : String?
    ) : Call<KakaoLocationSearchData>
}