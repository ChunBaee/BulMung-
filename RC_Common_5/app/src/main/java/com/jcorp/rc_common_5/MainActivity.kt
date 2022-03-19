package com.jcorp.rc_common_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jcorp.rc_common_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var mainFrag = MainFragment()
    private val viewModel by viewModels<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        observe()

        supportFragmentManager.beginTransaction().replace(R.id.MainContainer, mainFrag).commit()
    }

    private fun observe() {
        viewModel.fullScrolled.observe(this, Observer {
            when(it) {
                true -> {
                    binding.TopView.visibility = View.GONE
                    binding.bottomView.visibility = View.GONE
                }

                false -> {
                    binding.TopView.visibility = View.VISIBLE
                    binding.bottomView.visibility = View.VISIBLE
                }
            }
        })

        viewModel.isPlaying.observe(this, Observer {
            when(it) {

            }
        })
    }

}