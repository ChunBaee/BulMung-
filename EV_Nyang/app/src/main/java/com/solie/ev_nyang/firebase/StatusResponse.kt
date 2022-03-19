package com.solie.ev_nyang.firebase

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml (name = "response")
data class StatusResponse(
    @Element
    val header : StatusHeader,
    @Element
    val body : StatusBody
    )

@Xml(name = "header")
data class StatusHeader (
    @PropertyElement
    val resultCode : Int,
    @PropertyElement
    val resultMsg : String,
    @PropertyElement
    val totalCount : Int,
    @PropertyElement
    val pageNo : Int,
    @PropertyElement
    val numOfRows : Int
        )

@Xml (name = "body")
data class StatusBody (
    @Element (name = "items")
    val items : StatusItems
        )

@Xml
data class StatusItems (
    @Element (name = "item")
    val item : List<StatusItem>
        )

@Xml
data class StatusItem (
    @PropertyElement (name = "busiId") var busiId : String?,
    @PropertyElement (name = "statId") var statId : String?,
    @PropertyElement (name = "chgerId") var chgerId : Int?,
    @PropertyElement (name = "stat") var stat : Int?,
    @PropertyElement (name = "statUpdDt") var statUpdDt : Long?,
    @PropertyElement (name = "lastTsdt") var lastTsdt : Long?,
    @PropertyElement (name = "lastTedt") var lastTedt : Long?,
    @PropertyElement (name = "nowTsdt") var nowTsdt : Long?

        )