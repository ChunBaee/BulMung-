package com.chunb.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chunb.myapplication.data.MItem
import com.chunb.myapplication.databinding.ItemCampingDetailPagerBinding

class CampingDetailPagerAdapter : RecyclerView.Adapter<CampingDetailPagerAdapter.CampingDetailPagerViewHolder>() {

    private var imageList = mutableListOf<MItem>()

    inner class CampingDetailPagerViewHolder(val binding : ItemCampingDetailPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : MItem) {
            binding.imageUrl = item
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CampingDetailPagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCampingDetailPagerBinding.inflate(layoutInflater, parent, false)
        return CampingDetailPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampingDetailPagerViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setImageList(list : MutableList<MItem>) {
        imageList = list
        notifyDataSetChanged()
    }

}