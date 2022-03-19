package com.jcorp.e_vap.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseData {

    val currentUser
        get() = FirebaseAuth.getInstance().currentUser!!.uid

    val firebaseDatabase
        get() = FirebaseDatabase.getInstance()

    val chargerStore
        get() = FirebaseFirestore.getInstance().collection("ChargerDB")

    val chgerTypeStore
        get() = FirebaseFirestore.getInstance().collection("ChargerDB")

    val userStore
        get() = FirebaseFirestore.getInstance()
}