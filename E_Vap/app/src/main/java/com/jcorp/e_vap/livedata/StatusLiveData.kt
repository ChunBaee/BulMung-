package com.jcorp.e_vap.livedata

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.jcorp.e_vap.utils.FirebaseData

class StatusLiveData (boolean : Boolean, statNm : String) : LiveData<Boolean>(), FirebaseData {

    override val userStore: FirebaseFirestore
        get() = super.userStore

    val map = mutableMapOf<String, Any>()

    var mBool = boolean
    var mStatNm = statNm
    fun upLoadBookmark(){
        Log.d(TAG, "upLoadBookmark: ${mBool} // WANT UPLOAD BOOKMARK")
        when(mBool) {
            true -> {
                map[mStatNm] = mStatNm
                userStore
                    .collection(currentUser)
                    .document(mStatNm)
                    .set(map)
            }

            false -> {
                map[mStatNm] = FieldValue.delete()
                userStore
                    .collection(currentUser)
                    .document(mStatNm)
                    .delete()
            }
        }
    }
}