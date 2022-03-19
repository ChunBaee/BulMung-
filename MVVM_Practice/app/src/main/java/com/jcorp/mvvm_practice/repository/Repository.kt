package com.jcorp.mvvm_practice.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jcorp.mvvm_practice.model.Database
import com.jcorp.mvvm_practice.model.Model

class Repository (application: Application) {
    fun upLoad(model : Model) {
        Database.upLoad(model)
    }
    fun downLoad() : MutableList<Model>{
        return Database.downLoad()
    }
}