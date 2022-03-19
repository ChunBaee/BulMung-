package com.jcorp.e_vap

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI


class LoginActivity : AppCompatActivity() {


    private val REQUEST_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signIn()
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
                Log.d(TAG, "onActivityResult: LOGIN SUCCESS")
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d(TAG, "onActivityResult: LOGIN FAILED")
                signIn()
            }
        }
    }
}
    /*private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
        this.onSignInResult(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signIn()
    }

    private fun signIn() {
        val signInIntent =
            AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(getSelectedTheme())
            .setLogo(getSelectedLogo())
            .setAvailableProviders(getProviders())
            //.setTosAndPrivacyPolicyUrls("https://naver.com", "https://google.com")
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun getSelectedTheme(): Int {
        return AuthUI.getDefaultTheme()
    }

    private fun getSelectedLogo(): Int {
        return AuthUI.NO_LOGO
    }

    private fun getProviders(): MutableList<AuthUI.IdpConfig> {
        var providers: MutableList<AuthUI.IdpConfig> = mutableListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        return providers
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        Log.d(TAG, "onSignInResult: 로그인 결과창 옴")
        val response = result.idpResponse
        if(result.resultCode == RESULT_OK) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            Toast.makeText(applicationContext, "로그인성공", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(applicationContext, "로그인실패", Toast.LENGTH_SHORT).show()
            signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            signIn()
        }
    }*/

