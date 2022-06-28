package com.chunb.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chunb.myapplication.R
import com.chunb.myapplication.data.CampingListData
import com.chunb.myapplication.data.Item
import com.chunb.myapplication.databinding.ItemListRvBinding

class CampingListAdapter : RecyclerView.Adapter<CampingListAdapter.CampingListViewHolder>() {
    private var campingList = mutableListOf<Item>()
    private lateinit var listClickedListener : ListClickedListener

    interface ListClickedListener {
        fun listClickedListener (view : View, position : Int)
    }
    fun searchClickedListener(clickListener : ListClickedListener) {
        this.listClickedListener = clickListener
    }

    inner class CampingListViewHolder(val binding : ItemListRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Item) {
            binding.listItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampingListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListRvBinding>(layoutInflater, R.layout.item_list_rv, parent, false)

        return CampingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampingListViewHolder, position: Int) {
        holder.bind(campingList[position])

        holder.itemView.setOnClickListener {
            listClickedListener.listClickedListener(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return campingList.size
    }

    fun setCampingList(list : MutableList<Item>) {
        campingList = list
        notifyDataSetChanged()
    }
}