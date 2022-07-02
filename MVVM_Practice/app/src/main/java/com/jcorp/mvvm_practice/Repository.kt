package com.jcorp.mvvm_practice

import com.jcorp.mvvm_practice.data.MVVMInfo

class Repository {
    private var mUserList = mutableListOf<MVVMInfo>(
        MVVMInfo("가나다"),
        MVVMInfo("나다라"),
        MVVMInfo("다라마"),
        MVVMInfo("라마바"),
        MVVMInfo("마바사")
    )

    fun returnUserList() : MutableList<MVVMInfo> {
        return mUserList
    }

    fun addUserList(newUser : MVVMInfo) : MutableList<MVVMInfo> {
        mUserList.add(newUser)
        return mUserList
    }
}