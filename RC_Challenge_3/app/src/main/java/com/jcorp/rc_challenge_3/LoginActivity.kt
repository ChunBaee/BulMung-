package com.jcorp.rc_challenge_3

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jcorp.rc_challenge_3.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.cardFacebookLogin.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isLogin", true)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: onStart() 호출")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: onResume() 호출")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: onPause() 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: onDestroy() 호출")
    }

}