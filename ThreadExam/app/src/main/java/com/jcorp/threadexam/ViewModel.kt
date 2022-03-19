package com.jcorp.threadexam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Handler

class ViewModel : ViewModel() {

    private val _mLiveData = MutableLiveData<Int>()
    val mLiveData : LiveData<Int> =  _mLiveData

    fun setLive(i : Int) {
        _mLiveData.postValue(i)
    }
}