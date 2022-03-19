package com.jcorp.e_vap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.e_vap.databinding.ItemStatStatusBinding
import com.jcorp.e_vap.model.DialogItem


class StatusAdapter(private val context : Context) : RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {
    private lateinit var statusClickListener: StatusClickListener
    private var data = mutableListOf<DialogItem>()

    interface StatusClickListener {
        fun onClick(view: View, position: Int)
    }

    fun statusClickListener(statusClickListener: StatusClickListener) {
        this.statusClickListener = statusClickListener
    }

    class StatusViewHolder(val binding: ItemStatStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DialogItem) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val binding =ItemStatStatusBinding.inflate(LayoutInflater.from(context), parent, false)
        return StatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.item = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setRecycler(item: MutableList<DialogItem>) {
        data = item
        notifyDataSetChanged()
    }
}