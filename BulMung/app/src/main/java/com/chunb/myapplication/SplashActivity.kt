package com.chunb.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chunb.myapplication.databinding.ActivitySplashBinding
import com.chunb.myapplication.util.ThemeManager

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        if (!getSharedPreferences("UserSettings", MODE_PRIVATE).getBoolean("IsDark", false)) {
            Log.d("----", "onCreate: LIGHT")
            ThemeManager.applyTheme(ThemeManager.ThemeMode.LIGHT)
            setSplash()
        } else {
            Log.d("----", "onCreate: DARK")
            ThemeManager.applyTheme(ThemeManager.ThemeMode.DARK)
            setSplash()
        }
    }

    private fun setSplash() {
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("----", "onCreate: SPLASH")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}