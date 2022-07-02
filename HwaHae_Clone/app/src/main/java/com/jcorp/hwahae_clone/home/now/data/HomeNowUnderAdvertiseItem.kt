package com.jcorp.hwahae_clone.home.now.data

data class HomeNowUnderAdvertiseItem (
    var title : MutableList<Titles> = mutableListOf(),
    var rankingList : MutableList<HomeNowHotItem> = mutableListOf(),
    var shoppingList : MutableList<HomeNowShoppingItem> = mutableListOf(),
    var familyMonthList : MutableList<HomeNowVerticalItem> = mutableListOf(),
    var mdRecommendList : MutableList<HomeNowVerticalItem> = mutableListOf(),
    var beautyOnList : MutableList<HomeNowBeautyOnItem> = mutableListOf()
        )