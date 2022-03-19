package com.solie.ev_nyang.firebase

import android.content.ContentValues
import android.util.Log
import com.solie.ev_nyang.util.FirebaseData

object ChargerStatus : FirebaseData {
    fun updateStatus() {
        RetrofitManager.instance.getStatus(pageNo = 1,
            completion = { responseState, responseBody ->
                when (responseState) {
                    API.RESPONSE_STATE.OKAY -> {
                        Log.d(ContentValues.TAG, "API 호출 성공 : $responseBody ")
                    }
                    API.RESPONSE_STATE.FAIL -> {
                        Log.d(ContentValues.TAG, "API 호출 실패 : $responseBody")
                    }
                }
            }
        )
    }
}