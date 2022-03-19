package com.jcorp.sibal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    private val repository by lazy {
        Repository()
    }

    private val _text = MutableLiveData<String>()
    val text : LiveData<String> = _text

    private val _result = MutableLiveData<Int>()
    val result : LiveData<Int> = _result

    fun observeText(mText : String, position : Int) {
        _text.value = mText
        text.value?.let { checkLength(it, position) }
    }

    private fun checkLength(text : String, position: Int) {
        _result.value = repository.checkLength(text, position)
    }

}