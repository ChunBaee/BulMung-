package com.chunb.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chunb.myapplication.R
import com.chunb.myapplication.data.CampingListData
import com.chunb.myapplication.data.Item
import com.chunb.myapplication.databinding.ItemListRvBinding

class KeywordCampingListAdapter : RecyclerView.Adapter<KeywordCampingListAdapter.keywordCampingListViewHolder>() {
    private var keywordsList = mutableListOf<Item>()
    private lateinit var keywordListClickedListener : KeywordListClickedListener

    interface KeywordListClickedListener {
        fun keywordListClickedListener (view : View, position : Int)
    }

    fun keywordListClickedListener (keywordListClickedListener : KeywordListClickedListener) {
        this.keywordListClickedListener = keywordListClickedListener
    }

    inner class keywordCampingListViewHolder(val binding : ItemListRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Item) {
            binding.listItem = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): keywordCampingListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListRvBinding.inflate(layoutInflater, parent, false)
        return keywordCampingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: keywordCampingListViewHolder, position: Int) {
        holder.bind(keywordsList[position])

        holder.itemView.setOnClickListener {
            keywordListClickedListener.keywordListClickedListener(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return keywordsList.size
    }

    fun getKeywordList(list : MutableList<Item>) {
        keywordsList = list
        notifyDataSetChanged()
    }
}