package com.solie.mansworld.data

import androidx.lifecycle.LiveData
import com.firebase.ui.auth.data.model.User
import com.solie.mansworld.util.AuthenticationState

interface Repository {

    fun getUserLiveData() : LiveData<Pair<User?, AuthenticationState>>

}