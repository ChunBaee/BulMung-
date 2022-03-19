package com.solie.ev_nyang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solie.ev_nyang.item.FirebaseItem

class MarkerViewModel : ViewModel() {
    private val repo = MarkerRepository()
    private val _MkInfo = MutableLiveData<FirebaseItem>()

    val MkInfo : LiveData<FirebaseItem> = _MkInfo

    fun request(index : String) {
        repo.getMkStatInfo(index).let {
            _MkInfo.value = it
        }
    }
}