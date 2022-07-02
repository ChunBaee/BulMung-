package com.jcorp.hwahae_clone.home.now.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowHotRvBinding
import com.jcorp.hwahae_clone.home.now.data.HomeNowHotItem

class HomeNowHotAdapter : RecyclerView.Adapter<HomeNowHotAdapter.HomeNowHotViewHolder>() {
    private var hotList = mutableListOf<HomeNowHotItem>()

    inner class HomeNowHotViewHolder (val binding : ItemHomeNowHotRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowHotItem) {
            binding.hotItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNowHotViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeNowHotRvBinding>(layoutInflater, R.layout.item_home_now_hot_rv, parent, false)
        return HomeNowHotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowHotViewHolder, position: Int) {
        holder.bind(hotList[position])
    }

    override fun getItemCount(): Int {
        return hotList.size
    }


}