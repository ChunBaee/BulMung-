package com.solie.ev_nyang

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.solie.ev_nyang.databinding.ActivityMainBinding
import com.solie.ev_nyang.firebase.ChargerInfo
import com.solie.ev_nyang.util.FirebaseData
import com.solie.ev_nyang.util.NetworkStatus

class MainActivity : AppCompatActivity(), FirebaseData {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        networkCheck()

        binding.SetFB.setOnClickListener {
            ChargerInfo.getInfo(3)
            val intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)

        }

    }

    private fun networkCheck() {
        val networkStatus = NetworkStatus(applicationContext).checkNetworkStatus()
        if (!networkStatus) {
            Toast.makeText(applicationContext, "인터넷에 연결되지 않았습니다.", Toast.LENGTH_LONG).show()
            finish()
        } else {

        }
    }
}