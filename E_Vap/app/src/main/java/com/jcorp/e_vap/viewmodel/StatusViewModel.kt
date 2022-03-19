package com.jcorp.e_vap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jcorp.e_vap.livedata.StatusLiveData
import com.jcorp.e_vap.model.StatusModel
import java.lang.IllegalArgumentException

class StatusViewModel (boolean : Boolean, statNm : String) : ViewModel() {
    private val statusLiveData = StatusLiveData(boolean, statNm)
    fun getStatusLiveData() = statusLiveData

}

class StatusViewModelFactory (private val boolean : Boolean, private val statNm : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StatusViewModel::class.java)) {
            return StatusViewModel(boolean, statNm) as T
        }
        throw IllegalArgumentException("Un")
    }

}