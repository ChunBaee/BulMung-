package com.jcorp.rc_challenge_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.snackbar.Snackbar
import com.jcorp.rc_challenge_3.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "Main"
    private val LOGIN_REQUEST = 1000
    private var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: onCreate() 호출")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: onActivityResult() 호출")
        if (requestCode == LOGIN_REQUEST) {
            isLogin = data!!.getBooleanExtra("isLogin", false)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: OnStart() 호출")
        if (!isLogin) {
            val intent = Intent(this, LoginIDActivity::class.java)
            startActivityForResult(intent, LOGIN_REQUEST)
        } else {
            Snackbar.make(binding.mainContainer, "로그인에 성공하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: OnResume() 호출")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: onPause() 호출")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: onStop() 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: onDestroy() 호출")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: onRestart() 호출")
    }
}