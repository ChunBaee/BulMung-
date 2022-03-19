package com.jcorp.e_vap.repository

import com.jcorp.e_vap.livedata.MapLiveData

interface Repository {
    fun getMapLiveData() : MapLiveData

}