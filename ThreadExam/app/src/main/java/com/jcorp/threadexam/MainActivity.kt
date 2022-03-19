package com.jcorp.threadexam

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jcorp.threadexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<ViewModel>()
    private lateinit var pref : SharedPreferences
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        //upperThread()
        observe()
        setPref()

        binding.mainBtn.setOnClickListener(this)
    }

    private fun setPref() {
    }

    private fun observe() {
        viewModel.mLiveData.observe(this) {
            binding.mainText.text = it.toString()
        }
    }

    private fun upperThread() {
        Thread {
            while(true) {
                Log.d("////", "upperThread: RUN")
                count++
                viewModel.setLive(count)
                Thread.sleep(1000)
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.main_btn -> {
                pref = this.getPreferences(MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("STR", "STR1")
                editor.commit()
            }
        }
    }

}