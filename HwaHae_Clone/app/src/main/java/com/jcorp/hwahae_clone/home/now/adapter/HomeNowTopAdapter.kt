package com.jcorp.hwahae_clone.home.now.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowTopRvBinding
import com.jcorp.hwahae_clone.home.now.data.HomeNowTopRvItem

class HomeNowTopAdapter(context : Context) : RecyclerView.Adapter<HomeNowTopAdapter.HomeNowTopViewHolder>() {
    private var itemList = mutableListOf<HomeNowTopRvItem>()
    private val mContext = context

    inner class HomeNowTopViewHolder(val binding : ItemHomeNowTopRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowTopRvItem) {
            binding.topItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNowTopViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemHomeNowTopRvBinding>(layoutInflater, R.layout.item_home_now_top_rv, parent, false)
        return HomeNowTopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowTopViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList (list : MutableList<HomeNowTopRvItem>) {
        itemList = list.toMutableList()
        notifyDataSetChanged()
    }
}