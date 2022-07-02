package com.jcorp.hwahae_clone.home.beautyon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeBeautyonBannerPagerBinding
import com.jcorp.hwahae_clone.home.beautyon.data.HomeBeautyOnBannerItem

class HomeBeautyOnBannerAdapter : RecyclerView.Adapter<HomeBeautyOnBannerAdapter.HomeBeautyOnBannerViewHolder>() {
    private var mBannerList = mutableListOf<HomeBeautyOnBannerItem>()
    private var mNewBannerList = mutableListOf<HomeBeautyOnBannerItem>()

    inner class HomeBeautyOnBannerViewHolder(val binding : ItemHomeBeautyonBannerPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeBeautyOnBannerItem) {
            binding.bannerItem = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeBeautyOnBannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeBeautyonBannerPagerBinding>(layoutInflater, R.layout.item_home_beautyon_banner_pager, parent, false)
        return HomeBeautyOnBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBeautyOnBannerViewHolder, position: Int) {
        holder.bind(mNewBannerList[position])
    }

    override fun getItemCount(): Int {
        return mNewBannerList.size
    }

    fun setBannerList(list : MutableList<HomeBeautyOnBannerItem>) {
        mBannerList = list
        mNewBannerList = (listOf(mBannerList.last()) + mBannerList + listOf(mBannerList.first())) as MutableList
        notifyDataSetChanged()
    }
}