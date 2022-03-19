package com.jcorp.sibal

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jcorp.sibal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.btnPlus.setOnClickListener {
            viewModel.observeText(binding.edtMain.text.toString(), 1)
        }
        binding.btnMinus.setOnClickListener {
            viewModel.observeText(binding.edtMain.text.toString(), 2)
        }
        binding.btnTimes.setOnClickListener {
            viewModel.observeText(binding.edtMain.text.toString(), 3)
        }
        binding.btnDiv.setOnClickListener {
            viewModel.observeText(binding.edtMain.text.toString(), 4)
        }



        viewModel.text.observe(this, Observer {
            binding.txtMain.text = it
        })
        viewModel.result.observe(this, Observer {
            binding.txtResult.text = it.toString()
        })
        

    }
}