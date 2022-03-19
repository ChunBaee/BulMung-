package com.solie.ev_nyang.viewmodel

import com.solie.ev_nyang.item.FirebaseItem
import com.solie.ev_nyang.item.SearchMethod

class MarkerRepository {
    private var MkInfo = SearchMethod().searchStat("0")
    fun getMkStatInfo(_index : String) : FirebaseItem? {
        val _MkInfo = SearchMethod().searchStat(_index)
        _MkInfo?.let{
            MkInfo = _MkInfo
        }
        return MkInfo
    }
}