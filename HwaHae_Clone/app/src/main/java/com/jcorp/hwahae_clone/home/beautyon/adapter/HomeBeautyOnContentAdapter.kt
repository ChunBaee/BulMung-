package com.jcorp.hwahae_clone.home.beautyon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeBeautyonContentRvBinding
import com.jcorp.hwahae_clone.home.beautyon.data.HomeBeautyOnContentItem

class HomeBeautyOnContentAdapter : RecyclerView.Adapter<HomeBeautyOnContentAdapter.HomeBeautyOnContentViewHolder>() {
    private var contentList = mutableListOf<HomeBeautyOnContentItem>()

    inner class HomeBeautyOnContentViewHolder(val binding : ItemHomeBeautyonContentRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeBeautyOnContentItem) {
            binding.contentItem = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeBeautyOnContentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeBeautyonContentRvBinding>(layoutInflater, R.layout.item_home_beautyon_content_rv, parent, false)
        return HomeBeautyOnContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBeautyOnContentViewHolder, position: Int) {
        holder.bind(contentList[position])
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    fun setContentList(list : MutableList<HomeBeautyOnContentItem>) {
        contentList = list
        notifyDataSetChanged()
    }
}