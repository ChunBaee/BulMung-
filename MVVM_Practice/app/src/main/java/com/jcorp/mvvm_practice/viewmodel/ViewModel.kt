package com.jcorp.mvvm_practice.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.mvvm_practice.model.Model
import com.jcorp.mvvm_practice.repository.Repository

class ViewModel (application: Application) : ViewModel() {
    private val repository = Repository(application)
    private val _Data = MutableLiveData<MutableList<Model>>()

    val Data : LiveData<MutableList<Model>> = _Data

    fun upLoad(model : Model) {
        repository.upLoad(model)
    }
    fun downLoad() {
        _Data.value = repository.downLoad()
    }
}