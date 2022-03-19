package com.jcorp.rc_standard_4.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jcorp.rc_standard_4.R
import com.jcorp.rc_standard_4.ViewModel
import com.jcorp.rc_standard_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        viewModel.observeTimeMills()

        binding.btnWrite.setOnClickListener(this)
        binding.btnToolbarSave.setOnClickListener(this)
        binding.btnToolbarBack.setOnClickListener(this)
        binding.btnToolbarInfo.setOnClickListener(this)

        setVM()
        setMemoList()
    }

    private fun setVM() {
        viewModel.isRead.observe(this, Observer {
            when (it) {
                true -> {
                    binding.btnToolbarSave.visibility = View.VISIBLE
                    binding.bottomBtnGroup.visibility = View.VISIBLE

                    binding.txtMemoCount.visibility = View.GONE
                }

                false -> {
                    binding.btnToolbarSave.visibility = View.GONE
                    binding.bottomBtnGroup.visibility = View.GONE

                    binding.txtMemoCount.visibility = View.VISIBLE
                }
            }
        })

        viewModel.memoList.observe(this, Observer {
            binding.txtMemoCount.text = "${it.size} 개의 메모"
        })
        
        viewModel.isEdit.observe(this, Observer {
            when(it) {
                true ->supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.mainContainer, WriteFragment()).commit()
            }

        })

    }

    private fun hideKeyboard() {
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun setMemoList() {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemoListFragment())
            .commit()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_write ->
                if (viewModel.isRead.value == true) {
                } else {
                    supportFragmentManager.beginTransaction().addToBackStack(null)
                        .add(R.id.mainContainer, WriteFragment()).commit()
                }


            R.id.btn_toolbar_back, R.id.btn_toolbar_save -> {
                supportFragmentManager.beginTransaction().remove(WriteFragment()).commit()
                supportFragmentManager.popBackStack()
                hideKeyboard()
            }

            R.id.btn_toolbar_info -> {
                if(viewModel.isEdit.value == true) {
                    val dialog = WriteDialog()
                    dialog.show(supportFragmentManager, "WriteDialog")
                } else if(viewModel.isRead.value == false && viewModel.isEdit.value == false) {
                    viewModel.deleteAll()
                }
            }
        }
    }
}