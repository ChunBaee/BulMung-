package com.jcorp.e_vap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.e_vap.model.MapItem
import com.jcorp.e_vap.repository.Repository

class mViewModel constructor(
    private val repository: Repository
) : ViewModel() {

    private val _authState = MutableLiveData<Boolean>()
    val authState : LiveData<Boolean> = _authState

    private val _mapData = MutableLiveData<MapItem>()
    val mapData : LiveData<MapItem> = _mapData




    fun getAuthState(state : Boolean) {
        _authState.value = state
    }

    fun getMapData() {
        _mapData.value = repository.getMapLiveData().value
    }

}