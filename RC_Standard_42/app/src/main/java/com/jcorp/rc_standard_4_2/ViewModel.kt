package com.jcorp.rc_standard_4_2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {

    private val _checkList = MutableLiveData<MutableList<Boolean>>()
    val checkList : LiveData<MutableList<Boolean>> = _checkList

    private val _item = MutableLiveData<RvItem>()
    val item : LiveData<RvItem> = _item

    var checkItem = mutableListOf<Boolean>()

    private val _checkedPosition = MutableLiveData<Int>()
    val checkedPosition : LiveData<Int> = _checkedPosition

    fun setList() {
        _checkList.value = checkItem
        Log.d("////", "setList: ${checkList.value}")
    }

    fun editCheckPosition (position : Int) {
        checkItem[position] = !checkItem[position]
        _checkList.value = checkItem
        Log.d("////", "setList: ${checkList.value}")
    }


}