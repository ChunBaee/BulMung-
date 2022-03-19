package com.solie.myapplicationss.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirebaseViewModel : ViewModel() {
    val currentName : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}