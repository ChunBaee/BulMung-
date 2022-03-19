package com.solie.mansworld.sidemenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.solie.mansworld.R
import com.solie.mansworld.databinding.BoardlistItemShapeBinding

class SideMenuAdapter : RecyclerView.Adapter<SideMenuAdapter.SideMenuHolder>() {

    private var data = mutableListOf<SideMenuItem>()
    private lateinit var itemClickListener : ItemClickListener

    interface ItemClickListener {
        fun onClick (view : View, position : Int)
    }

    fun setItemClickListener (itemClickListener : ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideMenuHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<BoardlistItemShapeBinding>(layoutInflater, R.layout.boardlist_item_shape, parent, false)
        return SideMenuHolder(binding)
    }

    class SideMenuHolder (val binding : BoardlistItemShapeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sideMenuItem : SideMenuItem) {
            binding.sideMenuText.text = sideMenuItem.boardName
        }
    }

    override fun onBindViewHolder(holder: SideMenuHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun replaceList (newList : MutableList<SideMenuItem>) {
        data = newList.toMutableList()
        notifyDataSetChanged()
    }

}