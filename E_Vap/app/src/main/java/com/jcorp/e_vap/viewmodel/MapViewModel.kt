package com.jcorp.e_vap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jcorp.e_vap.livedata.MapLiveData
import java.lang.IllegalArgumentException

class MapViewModel (position : Int, choice : Int) : ViewModel() {
    private val mapLiveData = MapLiveData(position, choice)
    fun getMapLiveData() = mapLiveData

}
class MapViewModelFactory (private val position: Int,private val choice: Int ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(position, choice) as T
        }
        throw IllegalArgumentException("Un")
    }
}