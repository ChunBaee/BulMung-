package com.jcorp.rc_additional_1.retrofit

data class RevGeoData(
    var results: List<Area>
)

data class Area(
    var name: String?,
    var code: Code,
    var region: Region,
    var land: Land
)

data class Code(
    var id: String?,
    var type: String?,
    var mappingId: String?
)

data class Region(
    var area0: Area0,
    var area1: Area1,
    var area2: Area2,
    var area3: Area3,
    var area4: Area4,

)

data class Area0(
    var name: String?
)

data class Area1(
    var name: String?
)

data class Area2(
    var name: String?
)

data class Area3(
    var name: String?
)

data class Area4(
    var name: String?
)

data class Land(
    var type: String?,
    var number1: String?,
    var number2: String?
)
