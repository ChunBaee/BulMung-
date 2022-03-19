package com.jcorp.e_vap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jcorp.e_vap.livedata.TabLiveData
import com.jcorp.e_vap.model.MapItem
import java.lang.IllegalArgumentException

class TabViewModel (tabItem : Int): ViewModel () {
    private val tabLiveData = TabLiveData(tabItem)
    fun fetchData() : LiveData<MutableList<MapItem>> {
        val mLiveData = MutableLiveData<MutableList<MapItem>>()
        tabLiveData.getData().observeForever {
            mLiveData.value = it
        }
        return mLiveData
    }
}

class TabViewModelFactory (private val tabItem : Int ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TabViewModel::class.java)) {
            return TabViewModel(tabItem) as T
        }
        throw IllegalArgumentException("Un")
    }
}