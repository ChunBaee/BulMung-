package com.solie.ev_nyang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.solie.ev_nyang.adapter.StatusAdapter
import com.solie.ev_nyang.databinding.ActivityInfoBinding
import com.solie.ev_nyang.item.EachStatItem
import com.solie.ev_nyang.item.FirebaseItem
import com.solie.ev_nyang.util.FirebaseData
import com.solie.ev_nyang.util.RecyclerDecoration

class InfoActivity : AppCompatActivity(), FirebaseData {

    private lateinit var binding : ActivityInfoBinding
    private lateinit var stNm : String

    private val list = mutableListOf<FirebaseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)

        val intent = intent.getStringExtra("statId")
        stNm = intent!!

        setRecycler()

    }

    override fun onResume() {
        super.onResume()
        val intent = intent.getStringExtra("statId")
        stNm = intent!!
    }

    private fun setRecycler() {
        val adapter = StatusAdapter(this)
        binding.recyclerInfo.addItemDecoration(RecyclerDecoration(5,5,0,0))
        binding.recyclerInfo.adapter = adapter
        binding.recyclerInfo.setHasFixedSize(true)

        firebaseStore.document(stNm).collection(stNm)
            .get()
            .addOnSuccessListener {
                list.clear()
                for(i in it) {
                    list.add(i.toObject(FirebaseItem::class.java))
                }
                adapter.setRecycler(list)
            }
    }

}