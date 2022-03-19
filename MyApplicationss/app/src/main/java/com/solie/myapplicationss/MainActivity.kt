package com.solie.myapplicationss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.solie.myapplicationss.databinding.ActivityMainBinding
import com.solie.myapplicationss.viewmodel.FirebaseViewModel

class MainActivity : AppCompatActivity() {

    private val fmVmodel : FirebaseViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val nameObserver = Observer<String> { newName ->
            binding.textView.text = newName
        }
        fmVmodel.currentName.observe(this, nameObserver)

        binding.button.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101) {
            fmVmodel.currentName.setValue(data!!.getStringExtra("name"))
        }
    }

}