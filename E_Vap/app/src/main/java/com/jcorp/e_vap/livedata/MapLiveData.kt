package com.jcorp.e_vap.livedata

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jcorp.e_vap.model.MapItem
import com.jcorp.e_vap.utils.FirebaseData
import java.io.FileDescriptor.out
import java.util.*

class MapLiveData(position: Int, choice: Int) : LiveData<MapItem>(), FirebaseData {
    private var item: MapItem? = null
    private var mPosition = position
    private var mChoice = choice
    //private lateinit var mItem : MapItem

    enum class Type {
        Null,
        Chademo,
        ACType1,
        Chademo_Ac3,
        DcCombo,
        Chademo_DcCombo,
        Chademo_Ac3_DcCombo,
        Ac3
    }

    enum class Status(val status: Int) {
        InReady(2),
        InCharge(3),
        InInspection(5),
        InError(1)
    }

    override fun onActive() {
        super.onActive()
        when (mChoice) {
            0 -> startDownloadInfo()
        }
        when (mPosition) {
            0 -> setTypeFilterInfoDown()
            1 -> setStatusFilterInfoDown()
        }
    }

    private fun startDownloadInfo() {
        item = null
        chargerStore.get()
            .addOnSuccessListener { data ->
                for (document in data) {
                    item = document.toObject(MapItem::class.java)
                    setData(item!!)
                }
            }
    }

    private fun setTypeFilterInfoDown() {
        var list = mutableListOf<Any>(0, 0)
        item = null
        when (mChoice) {
            1 -> list.add(Type.ACType1)
            2 -> {
                list.add(Type.Chademo)
                list.add(Type.Chademo_Ac3)
                list.add(Type.Chademo_DcCombo)
                list.add(Type.Chademo_Ac3_DcCombo)
            }
            3 -> {
                list.add(Type.DcCombo)
                list.add(Type.Chademo_DcCombo)
                list.add(Type.Chademo_Ac3_DcCombo)
            }
            4 -> {
                list.add(Type.Ac3)
                list.add(Type.Chademo_Ac3)
                list.add(Type.Chademo_Ac3_DcCombo)
            }
        }
        chgerTypeStore.whereIn("chgerType", list)
            .get()
            .addOnSuccessListener { data ->
                for (document in data) {
                    item = document.toObject(MapItem::class.java)
                    Log.d(TAG, "setTypeFilterInfoDown: ${item!!.chgerType}")
                    setData(item!!)
                }
            }

    }

    private fun setStatusFilterInfoDown() {

    }

    private fun setData(item: MapItem) {
        value = item

    }

}