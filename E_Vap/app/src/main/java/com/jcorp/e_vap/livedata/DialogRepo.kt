package com.jcorp.e_vap.livedata

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jcorp.e_vap.model.DialogItem
class DialogRepo (statNm : String){
    var list = mutableListOf<DialogItem>()
    var mStatNm = statNm
    fun getData() : LiveData<MutableList<DialogItem>> {
        val mutableData = MutableLiveData<MutableList<DialogItem>>()

        val database = FirebaseDatabase.getInstance().getReference("ChargerDB")
            .orderByChild("statNm").equalTo(mStatNm)
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if(snapshot.exists()) {
                    for(snap in snapshot.children) {
                        snap.getValue(DialogItem::class.java)?.let { list.add(it) }
                        mutableData.value = list
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return mutableData
    }

}