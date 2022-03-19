package com.jcorp.mvvm_practice.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Database{
    companion object {
        fun upLoad(store : Model) {
            val mMap = mutableMapOf<String, Any>()
            mMap["Name"] = store.Name
            mMap["Number"] = store.Number
            mMap["Addr"] = store.Addr

            var fireStore = FirebaseFirestore.getInstance()
                .collection("DB")
                .document()
                .set(mMap)
        }

        fun downLoad () : MutableLiveData<MutableList<Model>> {
            val liveData = MutableLiveData<MutableList<Model>>()
            var list = mutableListOf<Model>()
            val firebase = FirebaseFirestore.getInstance()
                .collection("DB")
                .addSnapshotListener { value, error ->
                    if(error != null) {
                        Log.d(TAG, "downLoad: $error is error")
                    }
                    if(value != null) {
                        value.forEach {
                            list.add(it.toObject(Model::class.java))
                        }
                    }
                }
            liveData.value = list
            return liveData
        }
    }
}
