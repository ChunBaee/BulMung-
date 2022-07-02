package com.jcorp.hwahae_clone.home.now.data

data class HomeNowShoppingItem(
    var title: String = "",
    var subTitle: String = "",
    var pastPrice: Int = 0,
    var discountPercent: Int = 0,
    var currentPrice: Int = 0,
    var productImage: String = "",
    var optionsList: MutableList<String> = mutableListOf()
)

data class Titles(
    var title: String = "",
    var viewType : Int = 0,
)

data class Options(
    var option: String = ""
)