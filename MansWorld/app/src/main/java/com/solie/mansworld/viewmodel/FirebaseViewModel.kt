package com.solie.mansworld.viewmodel

import android.content.Intent
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.data.model.User
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.solie.mansworld.data.Repository
import com.solie.mansworld.util.AuthenticationState

class FirebaseViewModel constructor(
    private val repository: Repository
) : ViewModel() {


}