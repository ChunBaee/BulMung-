package com.solie.mansworld.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.solie.mansworld.R
import com.solie.mansworld.data.FirebaseData

class LoginActivity : AppCompatActivity(), FirebaseData {

    private val REQUEST_SIGN_IN = 1000
    private val OLD_USER = 0
    private val NEW_USER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signIn()
    }

    private fun signIn() {
        Log.d("DEVG", "로그인화면")
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
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
        return R.drawable.ranklist_icon
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
                Log.d("DEVG", "로그인성공")
                this.checkNewUser()
            } else {
                Log.d("DEVG", "로그인실패")
                signIn()
            }
        }
    }

    private fun checkNewUser() {
        Log.d("DEVG","인증시작 유저는 $userID")
        firebaseStore.document(userID)
            .get()
            .addOnSuccessListener { task ->
                if(task.exists()) {
                    Log.d("DEVG", "기존유저인증")
                    val intent = Intent().putExtra("UserCheck", OLD_USER)
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Log.d("DEVG", "신규유저인증")
                    val intent = Intent().putExtra("UserCheck", NEW_USER)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }.addOnFailureListener { e ->
                Log.d("DEVG", "$e 가 에러입니다.")
            }

    }

}