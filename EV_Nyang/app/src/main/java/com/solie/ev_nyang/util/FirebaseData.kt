package com.solie.ev_nyang.util

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

interface FirebaseData {

    val firebaseStorage
        get() = FirebaseStorage.getInstance().reference

    val firebaseDatabase
        get() = FirebaseDatabase.getInstance()

    val firebaseStore
        get() = FirebaseFirestore.getInstance().collection("ChargerDB")
}