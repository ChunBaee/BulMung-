package com.jcorp.mvvm_practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.mvvm_practice.data.MVVMInfo

class mViewModel : ViewModel() {
    val repo = Repository()

    private val _userData = MutableLiveData<MutableList<MVVMInfo>>()
    val userData : LiveData<MutableList<MVVMInfo>> = _userData

    fun getDataFromRepo() {
        _userData.value = repo.returnUserList()
    }

    fun addNewUserToRepo(user : MVVMInfo) {
        _userData.value = repo.addUserList(user)
    }
}