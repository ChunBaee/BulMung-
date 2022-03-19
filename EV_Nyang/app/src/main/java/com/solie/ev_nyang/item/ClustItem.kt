package com.solie.ev_nyang.item

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.clustering.ClusterItem
import com.solie.ev_nyang.util.FirebaseData

class ClustItem (lat : Double, lng : Double, title : String, snippet : String, status : Int, markerPosition : Int) : ClusterItem {

    private val position : LatLng
    private val title : String
    private val snippet : String
    private val status : Int
    private val markerPosition : Int

    fun getMarkerPosition() : Int {
        return markerPosition
    }

    fun getStatus() : Int {
        return status
    }

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    init {
        position = LatLng(lat,lng)
        this.title = title
        this.snippet = snippet
        this.status = status
        this.markerPosition = markerPosition
    }
}