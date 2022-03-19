package com.solie.ev_nyang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solie.ev_nyang.R
import com.solie.ev_nyang.databinding.ItemStatStatusBinding
import com.solie.ev_nyang.item.EachStatItem
import com.solie.ev_nyang.item.FirebaseItem

class StatusAdapter(private val context : Context) : RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {
    private var data = mutableListOf<FirebaseItem>()
    private lateinit var statusClickListener: StatusClickListener

    interface StatusClickListener {
        fun onClick(view: View, position: Int)
    }

    fun statusClickListener(statusClickListener: StatusClickListener) {
        this.statusClickListener = statusClickListener
    }

    class StatusViewHolder(val binding: ItemStatStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FirebaseItem) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val binding =ItemStatStatusBinding.inflate(LayoutInflater.from(context), parent, false)
        return StatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setRecycler(list: MutableList<FirebaseItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}