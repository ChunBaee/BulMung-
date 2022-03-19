package com.jcorp.e_vap.retrofit

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.jcorp.e_vap.utils.FirebaseData

object StatusInfo : FirebaseData {

    private var mStatNm: String = ""

    fun getStatus(pageNo: Int) {
        RetrofitManager.instance.getInfo(1, completion = { responseState, infoItems ->
            when (responseState) {
                API.RESPONSE_STATE.OKAY -> {
                    Log.d(TAG, "getStatus: API 호출 성공")
                    upLoadToFirebase(infoItems)
                }

                API.RESPONSE_STATE.FAIL -> {
                    Log.d(TAG, "getStatus: API 호출 실패")
                }
            }
        })
    }

    private fun upLoadToFirebase(response: InfoItems) {
        Log.d(ContentValues.TAG, "upLoadToFirebase: Called")
        val upLoadMap = mutableMapOf<String, Any>()
        val dataMap = mutableMapOf<String, Any>()
        val checkMap = mutableMapOf<String, Int>()

        for (item in response.item) {
            if(!checkIsSame(item.statId!!)) {
                checkMap.clear()
            }

            upLoadMap["statNm"] = item.statNm.toString()
            upLoadMap["addr"] = item.addr.toString()
            upLoadMap["useTime"] = item.useTime.toString()
            upLoadMap["busiNm"] = item.busiNm.toString()
            upLoadMap["powerType"] = item.powerType.toString()
            upLoadMap["lat"] = item.lat!!.toDouble()
            upLoadMap["lng"] = item.lng!!.toDouble()
            upLoadMap["chgerId"] = item.chgerId!!.toInt()
            upLoadMap["parkingFree"] = item.parkingFree.toString()
            upLoadMap["limitYn"] = item.limitYn.toString()
            upLoadMap["limitDetail"] = item.limitDetail.toString()
            upLoadMap["chgerType"] = item.chgerType!!.toInt()

            checkMap["${item.chgerId}"] = item.stat!!.toInt()

            upLoadMap["stat"] = checkMap

            dataMap["statNm"] = item.statNm.toString()
            dataMap["statId"] = item.statId.toString()
            dataMap["busiId"] = item.busiId.toString()
            dataMap["bnm"] = item.bnm.toString()
            dataMap["busiCall"] = item.busiCall.toString()
            dataMap["zcode"] = item.zcode.toString()
            dataMap["chgerId"] = item.chgerId!!.toInt()
            dataMap["limitYn"] = item.limitYn.toString()
            dataMap["limitDetail"] = item.limitDetail.toString()
            dataMap["parkingFree"] = item.parkingFree.toString()
            dataMap["note"] = item.note.toString()
            dataMap["chgerType"] = item.chgerType!!.toInt()
            dataMap["location"] = item.location.toString()
            dataMap["delYn"] = item.delYn.toString()
            dataMap["output"] = item.output!!.toInt()
            dataMap["delDetail"] = item.delDetail.toString()
            dataMap["method"] = item.method.toString()
            dataMap["chgerType"] = item.chgerType!!.toInt()
            dataMap["stat"] = item.stat!!.toInt()
            dataMap["statUpdDt"] = item.statUpdDt!!.toLong()
            dataMap["lastTsdt"] = item.lastTsdt!!.toLong()
            dataMap["lastTedt"] = item.lastTedt!!.toLong()
            dataMap["nowTsdt"] = item.nowTsdt!!.toLong()
            dataMap["stat"] = item.stat!!.toInt()

            chargerStore.document("${item.statId}")
                .set(upLoadMap)
            firebaseDatabase.reference.child("ChargerDB")
                .child("${item.statNm} - ${item.chgerId}")
                .setValue(dataMap)

            Log.d("TAG", "StatData_Uploaded")

            upLoadMap.clear()
            dataMap.clear()

        }
    }

    private fun checkIsSame(curName: String): Boolean {
        return if (mStatNm == curName) {
            mStatNm = curName
            true
        } else {
            mStatNm = curName
            false
        }
    }
}