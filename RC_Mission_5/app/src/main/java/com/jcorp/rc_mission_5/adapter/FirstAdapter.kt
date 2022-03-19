package com.jcorp.rc_mission_5.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.rc_common_5.data.FirstItem
import com.jcorp.rc_mission_5.R
import com.jcorp.rc_mission_5.databinding.ItemMainBinding

class FirstAdapter (context : Context) : RecyclerView.Adapter<FirstAdapter.FViewHolder>() {
    private var item = mutableListOf<FirstItem>()
    private var mContext = context

    inner class FViewHolder(private val binding : ItemMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (item : FirstItem) {
            binding.itemMainTitle.text = item.conceptTitle
            binding.itemMainAlbumMainTxt.text = item.title
            binding.itemMainAlbumSubTxt.text = item.subTitle
            Glide.with(mContext).load(item.albumImg)
                .into(binding.itemMainAlbumImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainBinding>(layoutInflater,
            R.layout.item_main, parent, false)

        return FViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun setItem (mItem : MutableList<FirstItem>) {
        item = mItem
        notifyDataSetChanged()
    }

}