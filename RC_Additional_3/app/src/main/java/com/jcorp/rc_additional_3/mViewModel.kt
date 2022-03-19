package com.jcorp.rc_additional_3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.rc_additional_3.retrofit.NaverData

class mViewModel : ViewModel() {
    var accessToken = MutableLiveData<String>()
    var isNaver = MutableLiveData<Boolean>()

    var _currentUser = MutableLiveData<NaverData>()
    var currentUser : LiveData<NaverData> = _currentUser

    fun setUserData (data : NaverData?) {
        _currentUser.value = data!!
        Log.d("----", "setUserData: ${currentUser.value!!.response.nickname}")
    }
}