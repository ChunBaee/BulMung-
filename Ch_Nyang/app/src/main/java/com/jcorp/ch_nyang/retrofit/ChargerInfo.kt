package com.jcorp.ch_nyang.retrofit

import android.content.ContentValues
import android.util.Log
import com.jcorp.ch_nyang.util.API
import com.jcorp.ch_nyang.util.FirebaseData
import com.jcorp.ch_nyang.util.InfoItems

object ChargerInfo : FirebaseData {
    fun getInfo(pageNo: Int) {
        RetrofitManager.instance.getInfo(
            pageNo = pageNo,
            completion = { responseState, responseBody ->
                when (responseState) {
                    API.RESPONSE_STATE.OKAY -> {
                        Log.d(ContentValues.TAG, "API 호출 성공 : $responseBody ")
                        upLoadToFirebase(responseBody)
                    }
                    API.RESPONSE_STATE.FAIL -> {
                        Log.d(ContentValues.TAG, "API 호출 실패 : $responseBody")
                    }
                }
            })
    }

    private fun upLoadToFirebase(response: InfoItems) {
        Log.d(ContentValues.TAG, "upLoadToFirebase: Called")
        val upLoadMap = mutableMapOf<String, Any>()
        val statMap = mutableMapOf<String, Any>()
        val typeMap = mutableMapOf<String, Any>()
        for (item in response.item) {
            upLoadMap["statNm"] = item.statNm.toString()
            upLoadMap["statId"] = item.statId.toString()
            upLoadMap["addr"] = item.addr.toString()
            upLoadMap["lat"] = item.lat!!.toDouble()
            upLoadMap["lng"] = item.lng!!.toDouble()
            upLoadMap["busiId"] = item.busiId.toString()
            upLoadMap["bnm"] = item.bnm.toString()
            upLoadMap["busiNm"] = item.busiNm.toString()
            upLoadMap["busiCall"] = item.busiCall.toString()
            upLoadMap["zcode"] = item.zcode.toString()
            //upLoadMap["chgerId"] = item.chgerId!!.toInt()
            upLoadMap["useTime"] = item.useTime.toString()
            upLoadMap["limitYn"] = item.limitYn.toString()
            upLoadMap["limitDetail"] = item.limitDetail.toString()
            upLoadMap["parkingFree"] = item.parkingFree.toString()
            upLoadMap["note"] = item.note.toString()
            //upLoadMap["chgerType"] = item.chgerType!!.toInt()
            upLoadMap["location"] = item.location.toString()
            upLoadMap["delYn"] = item.delYn.toString()
            upLoadMap["output"] = item.output!!.toInt()
            upLoadMap["delDetail"] = item.delDetail.toString()
            upLoadMap["method"] = item.method.toString()
            upLoadMap["powerType"] = item.powerType.toString()
            //upLoadMap["stat"] = item.stat!!.toInt()
            upLoadMap["statUpdDt"] = item.statUpdDt!!.toLong()
            upLoadMap["lastTsdt"] = item.lastTsdt!!.toLong()
            upLoadMap["lastTedt"] = item.lastTedt!!.toLong()
            upLoadMap["nowTsdt"] = item.nowTsdt!!.toLong()

            statMap["${item.chgerId}"] = item.stat!!.toInt()
            typeMap["${item.chgerId}"] = item.chgerType!!.toInt()

            firebaseStore.document("${item.statId}")
                .set(upLoadMap)
            Log.d("TAG", "StatData_Uploaded")

            firebaseDatabase.getReference("Stat").child(item.statId.toString())
                .push()
                .updateChildren(statMap)

            firebaseDatabase.getReference("Type").child(item.statId.toString())
                .push()
                .updateChildren(typeMap)
        }


    }
}