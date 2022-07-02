package com.jcorp.hwahae_clone.home.now.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowAdPagerBinding
import com.jcorp.hwahae_clone.home.now.data.HomeNowAdItem

class HomeNowAdAdapter : RecyclerView.Adapter<HomeNowAdAdapter.HomeNowAdViewHolder>() {
    private var adList = mutableListOf<HomeNowAdItem>()
    private var infiniteAdList = mutableListOf<HomeNowAdItem>()

    inner class HomeNowAdViewHolder(val binding: ItemHomeNowAdPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeNowAdItem) {
            binding.adItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNowAdViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeNowAdPagerBinding>(
            layoutInflater,
            R.layout.item_home_now_ad_pager,
            parent,
            false
        )
        return HomeNowAdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowAdViewHolder, position: Int) {
        holder.bind(infiniteAdList[position])
    }

    override fun getItemCount(): Int {
        return infiniteAdList.size
    }

    fun setAdList(list: MutableList<HomeNowAdItem>) {
        adList = list
        infiniteAdList = (mutableListOf(adList.last()) + adList + mutableListOf(adList.first())) as MutableList<HomeNowAdItem>
        notifyDataSetChanged()
    }
}