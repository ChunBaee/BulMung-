package com.jcorp.rc_mission_5.data

data class PlaylistItem (var album : String = "",
var title : String = "",
var singer : String = "",
var lyric : String = "",
var likeCount : Int? = 0,
var isPlaying : Boolean = false,
var runningTime : String = ""){
}