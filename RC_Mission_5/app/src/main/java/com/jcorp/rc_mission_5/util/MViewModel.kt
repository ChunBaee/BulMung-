package com.jcorp.rc_mission_5.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.rc_mission_5.data.PlaylistItem

class mViewModel : ViewModel() {

    //재생목록
    private val _Playlist = MutableLiveData<MutableList<PlaylistItem>>()
    val PlayList : LiveData<MutableList<PlaylistItem>> = _Playlist

    var mPlayList = mutableListOf<PlaylistItem>()
    var mHeartList = mutableListOf<Boolean>()

    var playPosition = MutableLiveData<Int>()
    var prePosition : Int? = null

    //현재 재생중(이었던) 곡 정보
    private val _nowPlaying = MutableLiveData<PlaylistItem>()
    val nowPlaying : LiveData<PlaylistItem> = _nowPlaying

    private val _isHeartPressed = MutableLiveData<MutableList<Boolean>>()
    val isHeartPressed : LiveData<MutableList<Boolean>> = _isHeartPressed

    //지금 재생중인지
    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying : LiveData<Boolean> = _isPlaying


    private val _toPlayFrag = MutableLiveData<Boolean>(false)
    val toPlayFrag : LiveData<Boolean> = _toPlayFrag

    private val _isToPlayList = MutableLiveData<Boolean> (false)
    val isToPlayList : LiveData<Boolean> = _isToPlayList



    fun heartPressed() {
        mHeartList[playPosition.value!!] = !mHeartList[playPosition.value!!]
        _isHeartPressed.value = mHeartList
    }


    fun setPlayList () {
        _Playlist.value = mPlayList
    }

    fun setNowPlaying (item : PlaylistItem) {
        _nowPlaying.value = item
    }

    fun setIsPlaying (state : Boolean) {
        _isPlaying.value = state
    }



    fun toPlayFrag (state : Boolean) {
        _toPlayFrag.value = state
    }

    fun isToPlayList (state : Boolean) {
        _isToPlayList.value = state
    }


}