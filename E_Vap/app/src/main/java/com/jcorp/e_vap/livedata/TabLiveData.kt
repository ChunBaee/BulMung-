package com.jcorp.e_vap.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcorp.e_vap.model.MapItem
import com.jcorp.e_vap.utils.FirebaseData

class TabLiveData (tabItem : Int) : LiveData<MapItem>(), FirebaseData{
    private lateinit var item : MapItem
    var mTabItem = tabItem
    var mTabString : String = ""

    fun getData() : LiveData<MutableList<MapItem>> {
        val mData = MutableLiveData<MutableList<MapItem>>()

        when(mTabItem) {
            0 -> {

            }
            1 ->{

            }
            2 -> {

            }
        }

        return mData
    }

    private fun setData(item : MapItem) {
        value = item
    }

}