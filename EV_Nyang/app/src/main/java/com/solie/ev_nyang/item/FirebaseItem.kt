package com.solie.ev_nyang.item

import android.content.ContentValues
import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


data class FirebaseItem(
    var statNm: String = "",
    var statId: String = "",
    var chgerId: Int = 9999,
    var chgerType: Int = 9999,
    var addr: String = "",
    var location: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var useTime: String = "",
    var busiId: String = "",
    var bnm: String = "",
    var busiNm: String = "",
    var busiCall: String = "",
    var stat: Int = 9999,
    var statUpdDt: Long = 0,
    var lastTsdt: Long = 0,
    var lastTedt: Long = 0,
    var nowTsdt: Long = 0,
    var powerType: String = "",
    var output: Int = 0,
    var method: String = "",
    var zcode: String = "",
    var parkingFree: String = "",
    var note: String = "",
    var limitYn: String = "",
    var limitDetail: String = "",
    var delYn: String = "",
    var delDetail: String = "",
)

class SearchMethod {

    val statList = mutableListOf<FirebaseItem>()

    fun searchStat(index : String) : FirebaseItem? {
        FirebaseFirestore.getInstance().collection("ChargerDB")
            .document(index)
            .collection(index)
            .addSnapshotListener { value, error ->
                if(error != null) {
                    Log.d(ContentValues.TAG, "searchStat: $error is ERROR")
                }
                if(value != null) {
                    value.forEach {
                        statList.add(it.toObject(FirebaseItem::class.java))
                    }
                }
            }
        return statList[0]
    }
}
