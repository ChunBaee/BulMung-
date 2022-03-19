package com.jcorp.e_vap.viewmodel

import androidx.lifecycle.*
import com.jcorp.e_vap.livedata.DialogRepo
import com.jcorp.e_vap.model.DialogItem
import java.lang.IllegalArgumentException

class DialogViewModel(item : String) : androidx.lifecycle.ViewModel() {
    private val repo = DialogRepo(item)
    fun fetchData() : LiveData<MutableList<DialogItem>> {
        val mutableData = MutableLiveData<MutableList<DialogItem>>()
        repo.getData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}

class DialogViewModelFactory (private val item : String ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DialogViewModel::class.java)) {
            return DialogViewModel(item) as T
        }
        throw IllegalArgumentException("Un")
    }
}