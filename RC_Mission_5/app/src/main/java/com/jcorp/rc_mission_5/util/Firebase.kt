package com.jcorp.rc_mission_5.util

import com.google.firebase.firestore.FirebaseFirestore

interface Firebase {
    val firstDataStore
        get() = FirebaseFirestore.getInstance().collection("FirstRV")

    val secondDataStore
        get() = FirebaseFirestore.getInstance().collection("SecondRV")

    val thirdDataStore
        get() = FirebaseFirestore.getInstance().collection("ThirdRV")

    val playListStore
        get() = FirebaseFirestore.getInstance().collection("PlayList")
}