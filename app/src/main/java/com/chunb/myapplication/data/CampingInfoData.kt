package com.chunb.myapplication.data

data class CampingInfoData(
    val response: Response
)

data class Response(
    val body: Body,
    val header: Header
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)
data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class Items(
    val item: MutableList<Item>
)

data class Item(
    var addr1: String? = "",
    var addr2: String? = "",
    var animalCmgCl: String? = "",
    var autoSiteCo: Int? = 0,
    var bizrno: String? = "",
    var brazierCl: String? = "",
    var caravAcmpnyAt: String? = "",
    var caravSiteCo: Int? = 0,
    var clturEvent: String? = "",
    var clturEventAt: String? = "",
    var contentId: Int? = 0,
    var createdtime: String? = "",
    var doNm: String? = "",
    var eqpmnLendCl: String? = "",
    var exprnProgrm: String? = "",
    var exprnProgrmAt: String? = "",
    var facltDivNm: String? = "",
    var facltNm: String? = "",
    var featureNm: String? = "",
    var firstImageUrl: String? = "",
    var glampInnerFclty: String? = "",
    var glampSiteCo: Int? = 0,
    var gnrlSiteCo: Int? = 0,
    var homepage: String? = "",
    var induty: String? = "",
    var indvdlCaravSiteCo: Int? = 0,
    var insrncAt: String? = "",
    var intro: String? = "",
    var lctCl: String? = "",
    var lineIntro: String? = "",
    var manageNmpr: Int? = 0,
    var manageSttus: String? = "",
    var mangeDivNm: String? = "",
    var mapX: Double = 0.0,
    var mapY: Double = 0.0,
    var mgcDiv: String? = "",
    var modifiedtime: String? = "",
    var operDeCl: String? = "",
    var operPdCl: String? = "",
    var posblFcltyCl: String? = "",
    var prmisnDe: String? = "",
    var resveCl: String? = "",
    var resveUrl: String? = "",
    var sbrsCl: String? = "",
    var sbrsEtc: String? = "",
    var sigunguNm: String? = "",
    var tel: String? = "",
    var themaEnvrnCl: String? = "",
    var tourEraCl: String? = "",
    var trlerAcmpnyAt: String? = "",
    var trsagntNo: String? = "",
    var zipcode: String? = ""
)