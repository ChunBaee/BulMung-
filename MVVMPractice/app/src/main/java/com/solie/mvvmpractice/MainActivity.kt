package com.solie.mvvmpractice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.solie.mvvmpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var list = mutableListOf<Exam1>()
    lateinit var model : MyViewModel

    private val observerListener = Observer<Exam1> {
        it?.let { exam1 ->  }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        if(::model.isInitialized) {
            model.mExam1.observe(this, observerListener)
        }
        setRecycler()
    }

    private fun setRecycler() {
        val adapter = RecyclerAdapter(this)

        list.add(Exam1("가", 1))
        list.add(Exam1("나",2))
        list.add(Exam1("다",1))
        list.add(Exam1("라",2))

        binding.mainRecycler.layoutManager = LinearLayoutManager(this)
        binding.mainRecycler.adapter = adapter
        adapter.setData(list)
        adapter.cListener(object : RecyclerAdapter.CListener{
            override fun onClick(view: View, position: Int) {
                model.request(position)
            }

        })
    }
}