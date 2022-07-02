package com.jcorp.hwahae_clone.home.beautyon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.hwahae_clone.home.beautyon.data.BeautyOnRepository
import com.jcorp.hwahae_clone.home.beautyon.data.HomeBeautyOnBannerItem
import com.jcorp.hwahae_clone.home.beautyon.data.HomeBeautyOnContentItem

class HomeBeautyOnViewModel : ViewModel() {
    private val repo = BeautyOnRepository()

    var curScroll = MutableLiveData<Int>()
    var pastScroll = MutableLiveData<Int>()
    var mainTabCurHeight = MutableLiveData<Int>()
    var mainTabHeight : Int = 0

    private val _homeBeautyOnBannerItem = MutableLiveData<MutableList<HomeBeautyOnBannerItem>>()
    val homeBeautyOnBannerItem : LiveData<MutableList<HomeBeautyOnBannerItem>> = _homeBeautyOnBannerItem

    private val _homeBeautyOnContentItem = MutableLiveData<MutableList<HomeBeautyOnContentItem>>()
    val homeBeautyOnContentItem : LiveData<MutableList<HomeBeautyOnContentItem>> = _homeBeautyOnContentItem

    fun setHomeBeautyOnBannerItem() {
        repo.setHomeBeautyOnBannerItem(_homeBeautyOnBannerItem)
    }

    fun setHomeBeautyOnContentItem() {
        repo.setHomeBeautyOnContentItem(_homeBeautyOnContentItem)
    }
}