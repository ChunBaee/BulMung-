package com.chunb.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chunb.myapplication.data.*
import com.naver.maps.geometry.LatLng
import com.skt.Tmap.TMapTapi

class ViewModel : ViewModel() {
    private val repo = Repository()

    var tMap : TMapTapi? = null
    val isCurLocation = MutableLiveData<Boolean>()
    val isKeyword = MutableLiveData<Boolean>()
    val isOnKeyword = MutableLiveData<Boolean>()
    val showFab = MutableLiveData<Boolean>()

    val mapSeekRange = MutableLiveData<Int>()
    val mThemeIsDark = MutableLiveData<Boolean>()
    val mNaviSelected = MutableLiveData<Int>()

    private val mLocation = MutableLiveData<LatLng>()

    //주소 검색시 카카오 주소찾기 api 데이터 담는 배열
    private val _kakaoLocationSearchData = MutableLiveData<MutableList<Place>>()
    val kakaoLocationSearchData: LiveData<MutableList<Place>> = _kakaoLocationSearchData

    //일반적인 캠핑장 정보 담는 배열
    private val _locationBasedCampingData = MutableLiveData<MutableList<Item>>()
    val locationBasedCampingData: LiveData<MutableList<Item>> = _locationBasedCampingData

    //키워드 검색한 캠핑장 정보 담는 배열
    private val _keywordBasedCampingData = MutableLiveData<MutableList<Item>>()
    val keywordBasedCampingData : LiveData<MutableList<Item>> = _keywordBasedCampingData

    //선택한 캠핑장 디테일 정보 담기
    private var _curSelectedCampingDetailData = MutableLiveData<Item>()
    val curSelectedCampingDetailData : LiveData<Item> = _curSelectedCampingDetailData

    private var _curSelectedCampingImageData = MutableLiveData<MutableList<MItem>>()
    val curSelectedCampingImageData : LiveData<MutableList<MItem>> = _curSelectedCampingImageData

    fun getkakaoLocationData(keyword: String) {
        //카카오 주소찾기 api
        repo.getKakaoLocationSearchData(_kakaoLocationSearchData, keyword)
    }
    //카카오 주소 찾아온 배열 초기화
    fun deleteKakaoLocationData() {
        _kakaoLocationSearchData.value = mutableListOf()
    }
    //키워드 주소 찾아온 배열 초기화
    fun deleteKeywordLocationData() {
        curSearchKeyword.value = ""
        _keywordBasedCampingData.value = mutableListOf()
    }

    fun getKeywordLocationData(keyword : String) {
        //키워드로 캠핑장 찾는 api
        repo.getSearchBasedCampingData(_keywordBasedCampingData, keyword)
    }

    fun setMapLocation(lat : Double, lng : Double) {
        mLocation.value = LatLng(lat, lng)
    }

    fun getMapLocation() : LatLng = mLocation.value!!

    fun setSearchedLocation(position: Int) {
        searchedLat.value = kakaoLocationSearchData.value!![position].x.toDouble()
        searchedLng.value = kakaoLocationSearchData.value!![position].y.toDouble()
        setMapLocation(kakaoLocationSearchData.value!![position].y.toDouble(), kakaoLocationSearchData.value!![position].x.toDouble())
        Log.d("----", "setNo(Searched): ${searchedLat.value}, ${searchedLng.value}")
        useSearchedLocation()
        getLocationData()
    }

    fun useUserLocation() {
        isCurLocation.value = true
    }
    private fun useSearchedLocation() {
        isCurLocation.value = false
    }
    fun keywordUsed() {
        isKeyword.value = isKeyword.value != true
    }

    fun setCurCampingData(position: Int) {
        Log.d("----", "setCurCampingData: ${locationBasedCampingData.value!![position].facltNm}")
        _curSelectedCampingDetailData.value = locationBasedCampingData.value!![position]
    }

    fun setCurCampingDataFromKeyword (position: Int) {
        _curSelectedCampingDetailData.value = keywordBasedCampingData.value!![position]
    }

    fun getCurCampingImageData(contentId : Int) {
        repo.getCampingImageListData(_curSelectedCampingImageData, contentId)
    }
    fun deleteCurCampingImageData() {
        _curSelectedCampingImageData.value = mutableListOf()
    }

    fun getLocationData() {
        Log.d("----", "getLocationData: ${mapSeekRange.value}")
        if(mLocation.value != null) {
            repo.getLocationBasedCampingData(
                _locationBasedCampingData,
                mLocation.value!!.latitude,
                mLocation.value!!.longitude,
                mapSeekRange.value!!
            )
        }
    }

    fun changeMapSeekRange(newRange : Int) {
        mapSeekRange.value = newRange
    }


    val curSearchKeyword = MutableLiveData<String>()

    val searchedLat = MutableLiveData<Double>()

    private val searchedLng = MutableLiveData<Double>()


    fun setCurSearchKeyword(keyword: String) {
        curSearchKeyword.value = keyword
    }
}