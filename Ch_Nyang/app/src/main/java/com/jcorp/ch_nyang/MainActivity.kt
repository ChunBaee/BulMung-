package com.jcorp.ch_nyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jcorp.ch_nyang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onOpenMap()

    }
    internal fun onOpenMap() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, MapFragment())
            .commitNow()
    }
}