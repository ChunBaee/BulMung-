package com.jcorp.mvvm_practice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.jcorp.mvvm_practice.R
import com.jcorp.mvvm_practice.databinding.ActivityMainBinding
import com.jcorp.mvvm_practice.model.Model
import com.jcorp.mvvm_practice.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater, this)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        
        binding.saveBtn.setOnClickListener {
            viewModel.upLoad(Model(binding.nameEdt.text.toString(), binding.numEdt.text.toString(), binding.adrEdt.text.toString()))
        }
    }
}