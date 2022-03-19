package com.jcorp.e_vap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jcorp.e_vap.livedata.BookmarkLiveData
import com.jcorp.e_vap.livedata.StatusLiveData
import java.lang.IllegalArgumentException

class BookmarkViewModel (boolean : Boolean?, statNm : String) : ViewModel() {
    private val checkBookMark = BookmarkLiveData(boolean, statNm)
    fun getCheckBookMark() = checkBookMark
}
class BookmarkViewModelFactory (private val boolean : Boolean?, private val statNm : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(boolean, statNm) as T
        }
        throw IllegalArgumentException("Un")
    }

}