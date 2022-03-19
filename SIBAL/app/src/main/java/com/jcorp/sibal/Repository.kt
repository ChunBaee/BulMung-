package com.jcorp.sibal

import androidx.lifecycle.LiveData

class Repository {
    private val carculateLiveData = CarculateLiveData()

    fun checkLength(text : String, position : Int) : Int {
        return carculateLiveData.carculate(text, position)
    }

}