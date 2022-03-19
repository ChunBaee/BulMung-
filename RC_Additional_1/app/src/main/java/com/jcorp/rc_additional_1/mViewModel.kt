package com.jcorp.rc_additional_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class mViewModel : ViewModel() {
    private val _orderXY : MutableLiveData<String> = MutableLiveData<String>()
    val orderXY : LiveData<String> = _orderXY

    val searchX = MutableLiveData<Double>()
    val searchY = MutableLiveData<Double>()

    private val _isSearch : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isSearch : LiveData<Boolean> = _isSearch


    fun setCurXY (x : Double, y : Double) {
        _orderXY.value = "$x,$y"
    }

    fun isSearchOrNot (state : Boolean) {
        _isSearch.value = state
    }
}