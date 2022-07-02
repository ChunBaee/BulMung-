package com.jcorp.hwahae_clone.home.now.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowNewRvBinding
import com.jcorp.hwahae_clone.home.now.data.HomeNowNewItem

class HomeNowNewAdapter(context : Context) : RecyclerView.Adapter<HomeNowNewAdapter.HomeNowNewViewHolder>(){
    private val mContext = context
    private var newItemList = mutableListOf<HomeNowNewItem>()

    inner class HomeNowNewViewHolder(val binding : ItemHomeNowNewRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowNewItem) {
            binding.newItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNowNewViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemHomeNowNewRvBinding>(layoutInflater, R.layout.item_home_now_new_rv, parent, false)
        return HomeNowNewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowNewViewHolder, position: Int) {
        holder.bind(newItemList[position])
    }

    override fun getItemCount(): Int {
        return newItemList.size
    }

    fun setNewItemList(list : MutableList<HomeNowNewItem>) {
        newItemList = list.toMutableList()
        notifyDataSetChanged()
    }
}