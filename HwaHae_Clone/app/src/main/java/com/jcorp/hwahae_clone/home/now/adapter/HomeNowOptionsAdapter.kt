package com.jcorp.hwahae_clone.home.now.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowOptionsRvBinding
import com.jcorp.hwahae_clone.home.now.data.Options

class HomeNowOptionsAdapter(optionsList : MutableList<Options>) : RecyclerView.Adapter<HomeNowOptionsAdapter.HomeNowOptionsViewHolder>() {
    private var mOptionsList = optionsList

    inner class HomeNowOptionsViewHolder(val binding : ItemHomeNowOptionsRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Options) {
            binding.optionsItem = item
            Log.d("----", "bind: $item")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNowOptionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeNowOptionsRvBinding>(layoutInflater, R.layout.item_home_now_options_rv, parent, false)
        return HomeNowOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowOptionsViewHolder, position: Int) {
        holder.bind(mOptionsList[position])
    }

    override fun getItemCount(): Int {
        return mOptionsList.size
    }
}