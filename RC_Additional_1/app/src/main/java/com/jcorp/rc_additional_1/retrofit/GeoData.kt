package com.jcorp.rc_additional_1.retrofit

data class GeoData(
    var status: String?,
    //var meta: List<meta>?,
    var addresses: List<address>?,
    var errorMessage: String?
)

data class address(
    var roadAddress: String?,
    var jibunAddress: String?,
    var englishAddress: String?,
    var addressElements: List<addressInfo>,
    var x : Double?,
    var y : Double?,
    var distance : Float?
)

data class addressInfo(
    var types: List<String?>,
    var longName: String?,
    var shortName: String?,
    var code: String?
)
