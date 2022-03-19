package com.jcorp.e_vap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.jcorp.e_vap.databinding.ItemTabFragmentBinding
import com.jcorp.e_vap.model.TabItem

class TabAdapter : RecyclerView.Adapter<TabAdapter.TabVH>() {

    private lateinit var tabClickListener : TabClickListener
    var data = listOf<TabItem>()

    interface TabClickListener {
        fun onClick (view : View, position: Int)
    }

    fun tabClickListener (tabClickListener: TabClickListener) {
        this.tabClickListener = tabClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTabFragmentBinding.inflate(layoutInflater, parent, false)

        return TabVH(binding)
    }

    override fun onBindViewHolder(holder: TabVH, position: Int) {
        holder.onBind(data[position])
        holder.itemView.setOnClickListener {
            tabClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class TabVH(val binding : ItemTabFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : TabItem) {
           binding.data = data
        }
    }
}