package com.jcorp.rc_standard_3

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.jcorp.rc_standard_3.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private val TAG = "WRITE"

    private var mTitle = ""
    private var mWon = ""
    private var mContent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)

        Log.d(TAG, "onCreate: onCreate() 호출")

        setToolbar()

        //손보
        binding.edtWon.addTextChangedListener {
            when (it?.length!! > 0) {
                true -> {
                    binding.txtWon.setTextColor(ResourcesCompat.getColor(resources, R.color.black, theme))
                    binding.cbxWon.setTextColor(ResourcesCompat.getColor(resources, R.color.black, theme))
                    binding.cbxWon.isClickable = true
                    when (binding.cbxWon.isChecked) {
                        true -> binding.cbxWon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#FFFB7E36"))

                        false -> binding.cbxWon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#D9808080"))
                    }

                }
                else -> {
                    binding.txtWon.setTextColor(ResourcesCompat.getColor(resources, R.color.light_gray, theme))
                    binding.cbxWon.setTextColor(ResourcesCompat.getColor(resources, R.color.light_gray, theme))
                    binding.cbxWon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#4D808080"))
                    binding.cbxWon.isClickable = false
                }
            }
        }

        binding.cbxWon.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked) {
                true -> binding.cbxWon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#FFFB7E36"))

                false -> binding.cbxWon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#D9808080"))
            }
        }

    }

    private fun setToolbar() {
        setSupportActionBar(binding.writeToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

    }

    override fun onStart() {
        mTitle = intent.getStringExtra("title").toString()
        if (mTitle != "null") {
            binding.edtTitle.setText(mTitle)
        }

        mWon = intent.getStringExtra("price").toString()

        if (mWon != "null") {
            binding.edtWon.setText(mWon)
        }

        mContent = intent.getStringExtra("content").toString()
        if (mContent != "null") {
            binding.edtContent.setText(mContent)
        }
        super.onStart()
        Log.d(
            TAG, "onStart: onStart() 호출"
        )

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: onResume() 호출")
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed: onBackPressed() 호출")
        val intent = Intent()
        intent.putExtra("title", binding.edtTitle.text.toString())
        intent.putExtra("price", binding.edtWon.text.toString())
        intent.putExtra("content", binding.edtContent.text.toString())
        setResult(RESULT_OK, intent)
        super.onBackPressed()
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

}