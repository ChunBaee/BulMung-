package com.jcorp.rc_mission_5.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcorp.rc_common_5.data.FirstItem
import com.jcorp.rc_mission_5.R
import com.jcorp.rc_mission_5.data.SecondItem
import com.jcorp.rc_mission_5.databinding.ItemMainBinding
import com.jcorp.rc_mission_5.databinding.ItemSecondBinding

class SecondAdapter (context : Context) : RecyclerView.Adapter<SecondAdapter.SViewHolder>() {
    private var item = mutableListOf<SecondItem>()
    private var mContext = context

    inner class SViewHolder(private val binding : ItemSecondBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (item : SecondItem) {
            binding.rvTitle.text = item.title
            binding.rvHashtag.text = item.hashtag
            Glide.with(mContext).load(item.album)
                .into(binding.rvAlbum)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSecondBinding>(layoutInflater,
            R.layout.item_second, parent, false)

        return SViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun setItem (mItem : MutableList<SecondItem>) {
        item = mItem
        notifyDataSetChanged()
    }

}