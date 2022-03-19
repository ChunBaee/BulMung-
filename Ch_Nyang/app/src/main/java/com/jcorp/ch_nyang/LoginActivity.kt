package com.jcorp.ch_nyang

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val REQUEST_SIGN_IN = 1000
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        if(auth.currentUser == null) {
            signIn()
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signIn() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(getSelectedTheme())
                .setLogo(getSelectedLogo())
                .setAvailableProviders(getProviders())
                .setTosAndPrivacyPolicyUrls("https://naver.com", "https://google.com")
                .setIsSmartLockEnabled(false)
                .build(), REQUEST_SIGN_IN
        )
    }

    private fun getSelectedTheme() : Int {
        return AuthUI.getDefaultTheme()
    }

    private fun getSelectedLogo() : Int {
        return AuthUI.NO_LOGO
    }

    private fun getProviders() : MutableList<AuthUI.IdpConfig> {
        var providers : MutableList<AuthUI.IdpConfig> = mutableListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        return providers
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_SIGN_IN) {
            if(resultCode == RESULT_OK) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                signIn()
            }
        }
    }
}