package com.solie.myapplicationss

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.solie.myapplicationss.databinding.ActivitySubBinding
import com.solie.myapplicationss.viewmodel.FirebaseViewModel

class SubActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySubBinding
    private val fmVmodel : FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub)

        binding.subButton.setOnClickListener {
            val names = binding.subEditText.text.toString()
            Toast.makeText(applicationContext, names+"가기전", Toast.LENGTH_SHORT).show()
            val intent = Intent()
            intent.putExtra("name", names)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}