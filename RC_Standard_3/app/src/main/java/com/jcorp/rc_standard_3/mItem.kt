package com.jcorp.rc_standard_3

data class mItem(var Img : Int = 0,
var title : String = "",
var location : String = "",
var time : String? = "",
var price : String? = "",
var chatTF : Boolean = false,
var chatCount : Int? = 0,
var heartTF : Boolean = false,
var heartCount : Int? = 0)
