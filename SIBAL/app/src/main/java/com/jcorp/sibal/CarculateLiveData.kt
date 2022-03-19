package com.jcorp.sibal

import androidx.lifecycle.LiveData

class CarculateLiveData () {
    private var mResult = 0

    fun carculate(text : String, position : Int) : Int{
        when(position) {
            1 -> mResult = text.toInt() + position
            2 -> mResult = text.toInt() - position
            3 -> mResult = text.toInt() * position
            4 -> mResult = text.toInt() / position
        }
        return mResult
    }
}