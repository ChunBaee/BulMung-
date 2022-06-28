package com.chunb.myapplication.data

data class CampingDetailImageData(
    val response: MResponse
)

data class MResponse(
    val body: MBody,
    val header: MHeader
)

data class MBody(
    val items: MItems,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class MHeader(
    val resultCode: String,
    val resultMsg: String
)

data class MItems(
    val item: List<MItem>
)
data class MItem(
    val contentId: Int,
    val createdtime: String,
    val imageUrl: String,
    val modifiedtime: String,
    val serialnum: Int
)