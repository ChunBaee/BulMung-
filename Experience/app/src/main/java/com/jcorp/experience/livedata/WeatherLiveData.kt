package com.jcorp.experience.livedata

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.jcorp.experience.model.WeatherInfoItem
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class WeatherLiveData (nx: Int, ny: Int): LiveData<MutableList<WeatherInfoItem>>() {
    private val firestore = FirebaseFirestore.getInstance()
    private var mWeather= mutableListOf<WeatherInfoItem>()
    private var weather = WeatherInfoItem()

    val mNx = nx
    val mNy = ny

    override fun onActive() {
        super.onActive()
        getWeatherData(mNx, mNy)
    }

    fun getWeatherData(nx: Int, ny: Int){
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                getData(nx ,ny)
            }
            setItem(mWeather)
        }

    }

    private suspend fun getData(nx : Int, ny : Int) : Boolean{
        return try {
            firestore.collection("WeatherDB")
                .document("$ny _ $nx")
                .collection("20211206")
                .get()
                .addOnSuccessListener {
                    mWeather = it.toObjects(WeatherInfoItem::class.java)
                }.await()
            true
        } catch (e : FirebaseException) {
            Log.d(TAG, "getData: $e")
            false
        }
    }

    private fun setItem(items : MutableList<WeatherInfoItem>) {
        value = items
    }
}