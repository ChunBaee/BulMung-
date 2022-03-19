package com.jcorp.ch_nyang.util

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseData {

    val firebaseDatabase
        get() = FirebaseDatabase.getInstance()

    val firebaseStore
        get() = FirebaseFirestore.getInstance().collection("ChargerDB")
}