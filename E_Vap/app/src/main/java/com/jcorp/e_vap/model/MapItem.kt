package com.jcorp.e_vap.model

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapItem(
    var addr: String = "",
    var busiNm: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var powerType: String = "",
    var statNm: String = "",
    var useTime: String = "",
    var chgerType: Int = 0,
    var limitYn: String = "",
    var limitDetail: String = "",
    var parkingFree: String = "",
    var stat: HashMap<String, Int> = HashMap(),
) : ClusterItem {

    private val position: LatLng
    private val title: String
    private val snippet: String
    private val status: MutableList<Int>

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String {
        return snippet
    }

    fun getStatus(): MutableList<Int> {
        Log.d(TAG, "getStatus: $status //CHGERSTATUS")
        return status
    }

    init {
        this.position = LatLng(lat, lng)
        this.title = statNm
        this.snippet = addr
        this.status = stat.values.toMutableList()
        Log.d(TAG, "checkSTAT:$status // $statNm // CHECKSTATUS")

        /*Log.d(TAG, "MSTAT: ${statList} // ${statNm} // MSTAT")
        if(statList.all{it == 2}) {
            this.status = 2
        } else if(statList.all {it == 3}) {
            this.status = 3
        } else if(statList.all {it == 5}) {
            this.status = 5
        } else if(statList.any{it == 2} && statList.any{it == 5}) {
            this.status = 25
        } else if(statList.any { it == 2 } && statList.any { it == 9 }) {
            this.status = 29
        }*/
    }

}
