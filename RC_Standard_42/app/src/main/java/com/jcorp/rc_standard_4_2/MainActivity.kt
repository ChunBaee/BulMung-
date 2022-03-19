package com.jcorp.rc_standard_4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jcorp.rc_standard_4_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<ViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var list = mutableListOf<String>()
        var bools = mutableListOf<Boolean>()

        var count = 0

        binding.lifecycleOwner = this


        for (i in 0 until 20) {
            list.add("$i")
            bools.add(false)
        }

        viewModel.checkItem = bools
        viewModel.setList()


        val adapter = RvAdapter()
        binding.rvView.adapter = adapter
        adapter.setList(list, viewModel.checkItem)

        viewModel.checkList.observe(this, Observer {
            adapter.editBool(it)
        })


        adapter.checkedListener(object : RvAdapter.CheckedListener {
            override fun onChecked(view: View, position: Int) {
                viewModel.editCheckPosition(position)
            }

        })
    }
}