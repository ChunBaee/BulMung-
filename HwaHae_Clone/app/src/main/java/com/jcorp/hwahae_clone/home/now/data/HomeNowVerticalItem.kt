package com.jcorp.hwahae_clone.home.now.data

data class HomeNowVerticalItem(
    var productImage : String = "",
    var title : String = "",
    var company : String = "",
    var pastPrice : Int = 0,
    var discountPercent : Int = 0,
    var currentPrice : Int = 0,
    var optionsList : MutableList<String> = mutableListOf()
)
