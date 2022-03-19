package com.jcorp.experience.retrofit

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml (name = "response")
data class WeatherResponse (
    @Element
    val header : WeatherHeader,
    @Element
    val body : WeatherBody,
    )

@Xml(name = "header")
data class WeatherHeader (
    @PropertyElement
    val resultCode : Int,
    @PropertyElement
    val resultMsg : String,
    )

@Xml(name = "body")
data class WeatherBody (
    @PropertyElement
    val numOfRows : Int,
    @PropertyElement
    val pageNo : Int,
    @PropertyElement
    val totalCount : Int,
    @PropertyElement
    val dataType : String,
    @Element (name = "items")
    val items : WeatherItems
        )

@Xml
data class WeatherItems(
    @Element(name = "item")
    val item : List<WeatherItem>
)

@Xml
data class WeatherItem (
    @PropertyElement (name = "baseDate") var baseDate : String?,
    @PropertyElement (name = "baseTime") var baseTime : String?,
    @PropertyElement (name = "category") var category : String?,
    @PropertyElement (name = "fcstDate") var fcstDate : String?,
    @PropertyElement (name = "fcstTime") var fcstTime : String?,
    @PropertyElement (name = "fcstValue") var fcstValue : String?,
    @PropertyElement (name = "nx") var nx : Int?,
    @PropertyElement (name = "ny") var ny : Int?
)
