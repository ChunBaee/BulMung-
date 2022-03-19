package com.solie.mansworld.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

interface FirebaseData {
    val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    val userID: String
        get() = currentUser!!.uid

    val firebaseStore
        get() = FirebaseFirestore.getInstance().collection("UserDB")

    val firebaseStorage: StorageReference
        get() = FirebaseStorage.getInstance().reference

    val databaseAllContent: DatabaseReference
        get() = FirebaseDatabase.getInstance().getReference("Contents")

    val databaseKongJi: Query
        get() = FirebaseDatabase.getInstance().getReference("Contents").orderByChild("BoardName").equalTo("공지사항")

    val databaseFree: Query
        get() = FirebaseDatabase.getInstance().getReference("Contents").orderByChild("BoardName").equalTo("자유 게시판")

    val databaseTip: Query
        get() = FirebaseDatabase.getInstance().getReference("Contents").orderByChild("BoardName").equalTo("팁과 노하우")

    val databaseAsk: Query
        get() = FirebaseDatabase.getInstance().getReference("Contents").orderByChild("BoardName").equalTo("질문 게시판")

}