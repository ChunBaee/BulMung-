package com.jcorp.e_vap.retrofit

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class InfoResponse(
    @Element
    val header: InfoHeader,
    @Element
    val body: InfoBody,
)
@Xml(name = "header")
data class InfoHeader(
    @PropertyElement
    val resultCode: Int,
    @PropertyElement
    val resultMsg: String,
    @PropertyElement
    val numOfRows: Int,
    @PropertyElement
    val pageNo: Int,
    @PropertyElement
    val totalCount: Int
)

@Xml(name = "body")
data class InfoBody(
    @Element (name = "items")
    val items: InfoItems
)

@Xml
data class InfoItems(
    @Element(name = "item")
    val item: List<InfoItem>
)

@Xml
data class InfoItem(
    @PropertyElement (name = "statNm") var statNm : String?,
    @PropertyElement (name = "statId") var statId: String?,
    @PropertyElement (name = "chgerId") var chgerId : Int?,
    @PropertyElement (name = "chgerType") var chgerType : Int?,
    @PropertyElement (name = "addr") var addr : String?,
    @PropertyElement (name = "location") var location : String?,
    @PropertyElement (name = "lat") var lat : Double?,
    @PropertyElement (name = "lng") var lng : Double?,
    @PropertyElement (name = "useTime") var useTime : String?,
    @PropertyElement (name = "busiId") var busiId : String?,
    @PropertyElement (name = "bnm") var bnm : String?,
    @PropertyElement (name = "busiNm") var busiNm : String?,
    @PropertyElement (name = "busiCall") var busiCall : String?,
    @PropertyElement (name = "stat") var stat : Int?,
    @PropertyElement (name = "statUpdDt") var statUpdDt : Long?,
    @PropertyElement (name = "lastTsdt") var lastTsdt : Long?,
    @PropertyElement (name = "lastTedt") var lastTedt : Long?,
    @PropertyElement (name = "nowTsdt") var nowTsdt : Long?,
    @PropertyElement (name = "powerType") var powerType : String?,
    @PropertyElement (name = "output") var output : Int?,
    @PropertyElement (name = "method") var method : String?,
    @PropertyElement (name = "zcode") var zcode : Int?,
    @PropertyElement (name = "parkingFree") var parkingFree : String?,
    @PropertyElement (name = "note") var note : String?,
    @PropertyElement (name = "limitYn") var limitYn : String?,
    @PropertyElement (name = "limitDetail") var limitDetail : String?,
    @PropertyElement (name = "delYn") var delYn : String?,
    @PropertyElement (name = "delDetail") var delDetail : String?,
)
