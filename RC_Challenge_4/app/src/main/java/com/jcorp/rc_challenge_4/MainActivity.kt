package com.jcorp.rc_challenge_4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_challenge_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = RvAdapter()
        binding.rvView.adapter = adapter


        val list = mutableListOf<RvItem>()
        list.add(RvItem(R.drawable.img_profile1, "윤민정", "10시 반 회의", "오후 8:24"))
        list.add(RvItem(R.drawable.img_profile2, "트윙클", "비밀메시지", "오후 8:20", 3, true))
        list.add(RvItem(R.drawable.img_profile3, "동호회모임", "다시 해보자", "오후 4:45", 36, true))
        list.add(RvItem(R.drawable.img_profile4, "언니", "언제까지?", "오후 2:34"))

        adapter.setList(list)

        val swipeHelperCallback = SwipeHelperCallback().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 3)
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvView)
        binding.rvView.apply {
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
        }
        adapter.onItemLongClickListener(object : RvAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                adapter.removeData(position)
            }
        })



    }

}