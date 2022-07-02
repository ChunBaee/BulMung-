package com.jcorp.hwahae_clone.home.now

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.hwahae_clone.home.now.data.*

class HomeNowViewModel : ViewModel() {
    private val homeRepo = HomeRepository()

    private val _homeNowCategoryData = MutableLiveData<MutableList<HomeNowTopRvItem>>()
    val homeNowCategoryData : LiveData<MutableList<HomeNowTopRvItem>> = _homeNowCategoryData

    private val _homeNowNewProductData = MutableLiveData<MutableList<HomeNowNewItem>>()
    val homeNowNewProductData : LiveData<MutableList<HomeNowNewItem>> = _homeNowNewProductData

    private val _homeNowAdvertiseData = MutableLiveData<MutableList<HomeNowAdItem>>()
    val homeNowAdvertiseData : LiveData<MutableList<HomeNowAdItem>> = _homeNowAdvertiseData

    private val _homeNowUnderAdvertiseTitleData = MutableLiveData<MutableList<Titles>>()
    val homeNowUnderAdvertiseTitleData : LiveData<MutableList<Titles>> = _homeNowUnderAdvertiseTitleData

    private val _homeNowHotRankingData = MutableLiveData<MutableList<HomeNowHotItem>>()
    val homeNowHotRankingData : LiveData<MutableList<HomeNowHotItem>> = _homeNowHotRankingData

    private val _homeNowShoppingData = MutableLiveData<MutableList<HomeNowShoppingItem>>()
    val homeNowShoppingData : LiveData<MutableList<HomeNowShoppingItem>> = _homeNowShoppingData

    private val _familyMonthData = MutableLiveData<MutableList<HomeNowVerticalItem>>()
    val familyMonthData : LiveData<MutableList<HomeNowVerticalItem>> = _familyMonthData

    private val _mdRecommendData = MutableLiveData<MutableList<HomeNowVerticalItem>>()
    val mdRecommendData : LiveData<MutableList<HomeNowVerticalItem>> = _mdRecommendData

    private val _beautyOnData = MutableLiveData<MutableList<HomeNowBeautyOnItem>>()
    val beautyOnData : LiveData<MutableList<HomeNowBeautyOnItem>> = _beautyOnData


    fun setHomeNowCategoryData() {
        _homeNowCategoryData.value = homeRepo.setTopRvItem()
    }
    fun setHomeNowNewProductData() {
        homeRepo.setNewProductData(_homeNowNewProductData)
    }
    fun setHomeNowAdvertiseData() {
        homeRepo.setNewAdvertiseData(_homeNowAdvertiseData)
    }
    fun setHomeNowUnderAdvertiseTitleData() {
        _homeNowUnderAdvertiseTitleData.value = homeRepo.setHomeUnderAdvertiseTitleData()
    }
    fun setHomeNowHotRankingData() {
        _homeNowHotRankingData.value = homeRepo.setHomeHotRankingData()
    }
    fun setHomeNowShoppingData() {
        homeRepo.setHomeShoppingData(_homeNowShoppingData)
    }
    fun setFamilyMonthData() {
        homeRepo.setFamilyMonthData(_familyMonthData)
    }
    fun setMdRecommendData() {
        homeRepo.setMdRecommendData(_mdRecommendData)
    }

    fun setBeautyOnData() {
        homeRepo.setBeautyOnData(_beautyOnData)
    }

}