package com.jcorp.e_vap.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.jcorp.e_vap.utils.FirebaseData

class BookmarkLiveData (boolean : Boolean?, statNm : String) : LiveData<Boolean>(), FirebaseData {
    override val userStore: FirebaseFirestore
        get() = super.userStore

    var mBool = boolean
    var mStatNm = statNm

    override fun onActive() {
        super.onActive()
        checkBookmark()
    }

    fun checkBookmark(){
        userStore.collection(currentUser)
            .document(mStatNm)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mBool = it.result.exists()
                    value = mBool
                }
            }
    }
}