package com.jcorp.e_vap

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.jcorp.e_vap.databinding.ActivityInfoBinding
import com.jcorp.e_vap.utils.RecyclerDecoration
import com.jcorp.e_vap.viewmodel.DialogViewModel
import com.jcorp.e_vap.viewmodel.DialogViewModelFactory


class InfoActivity : AppCompatActivity(){

    private lateinit var viewModel : DialogViewModel
    private lateinit var viewModelFactory: DialogViewModelFactory
    private lateinit var binding : ActivityInfoBinding
    private lateinit var stNm : String

    private lateinit var adapter : StatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        binding.lifecycleOwner = this

        val intent = intent.getStringExtra("statNm")
        stNm = intent!!
        Log.d(TAG, "onCreate: ${stNm} / GET!!!!")

        setRecycler()
        getInfoData()

    }

    override fun onResume() {
        super.onResume()
        val intent = intent.getStringExtra("statNm")
        stNm = intent!!
    }

    private fun getInfoData() {
        var mStnm = MutableLiveData<String>()
        mStnm.value = stNm
        mStnm.observe(this, {
            viewModelFactory = DialogViewModelFactory(mStnm.value.toString())
            viewModel = ViewModelProvider(this, viewModelFactory).get(DialogViewModel::class.java)
            viewModel.fetchData().observe(this, Observer {
                adapter.setRecycler(it)
                adapter.notifyDataSetChanged()
            })
        })
    }

    private fun setRecycler() {
        adapter = StatusAdapter(this)
        binding.recyclerInfo.addItemDecoration(RecyclerDecoration(5,5,0,0))
        binding.recyclerInfo.adapter = adapter
        binding.recyclerInfo.setHasFixedSize(true)

    }

}