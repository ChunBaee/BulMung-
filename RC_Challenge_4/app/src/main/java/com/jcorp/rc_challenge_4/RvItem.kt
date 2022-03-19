package com.jcorp.rc_challenge_4

data class RvItem(
    var img : Int = 0,
    var title : String = "",
    var content : String = "",
    var time : String = "",
    var member : Int? = 0,
    var isGroup : Boolean? = false
)
