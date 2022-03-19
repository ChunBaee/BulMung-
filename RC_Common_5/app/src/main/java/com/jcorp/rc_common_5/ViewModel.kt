package com.jcorp.rc_common_5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {

    private val _slidingOffset = MutableLiveData<Float>(0F)
    val slidingOffset : LiveData<Float> = _slidingOffset

    private val _fullScrolled = MutableLiveData<Boolean>(false)
    val fullScrolled : LiveData<Boolean> = _fullScrolled

    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying : LiveData<Boolean> = _isPlaying

    fun getOffset(offset : Float) {
        _slidingOffset.value = offset
        isFullScrolled(slidingOffset.value)
    }

    private fun isFullScrolled(offset : Float?) {
        _fullScrolled.value = offset == 1.0F
    }

    fun setPlayingState (state : Boolean) {
        _isPlaying.value = state
    }

}