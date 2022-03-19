package com.jcorp.rc_standard_4

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.rc_standard_4.data.MemoListItem
import java.util.*

class ViewModel : ViewModel() {

    private val _mTimeMills = MutableLiveData<Long>()
    private val mTimeMills : LiveData<Long> = _mTimeMills

    private val _curTime = MutableLiveData<Date>()
    val curTime : LiveData<Date> = _curTime

    var mMemoList = mutableListOf<MemoListItem>()

    private val _memoList = MutableLiveData<MutableList<MemoListItem>>()
    val memoList : LiveData<MutableList<MemoListItem>> = _memoList

    private val _isEdit = MutableLiveData<Boolean>(false)
    val isEdit : LiveData<Boolean> = _isEdit

    private val _isRead = MutableLiveData<Boolean>(false)
    val isRead : LiveData<Boolean> = _isRead

    private val _isSave = MutableLiveData<Boolean>(false)
    val isSave : LiveData<Boolean> = _isSave

    private val _isDeleted = MutableLiveData<Boolean>(false)
    val isDeleted : LiveData<Boolean> = _isDeleted

    private val _editPosition = MutableLiveData<Int>()
    val editPosition : LiveData<Int> = _editPosition

    fun observeTimeMills() {
        _mTimeMills.value = System.currentTimeMillis()
        observeCurTime(Date(mTimeMills.value!!))
    }

    private fun observeCurTime(curTime : Date) {
        _curTime.value = curTime
    }

    fun editPosition (position : Int) {
        _editPosition.value = position
    }

    fun isEditState (state : Boolean) {
        _isEdit.value = state
    }

    fun isReadState (state : Boolean) {
        _isRead.value = state
    }

    fun isSaveState (state : Boolean) {
        _isSave.value = state
    }

    fun isDeletedState (state : Boolean) {
        _isDeleted.value = state
    }

    fun addMemo (memo : MemoListItem) {
        mMemoList.add(0, memo)
        setMemoList(mMemoList)
    }

    fun editMemo (memo : MemoListItem) {
        mMemoList[editPosition.value!!] = memo
        setMemoList(mMemoList)
    }

    fun deleteMemo (position : Int) {
        mMemoList.removeAt(position)
        setMemoList(mMemoList)
    }

    fun deleteAll() {
        mMemoList = mutableListOf<MemoListItem>()
        setMemoList(mMemoList)
    }

    private fun setMemoList(memo : MutableList<MemoListItem>) {
        Log.d("/WRITE/", "addMemoList: $memo")
        _memoList.value = memo
        Log.d("/WRITE/", "addMemoList: ${memoList.value}")
    }
}