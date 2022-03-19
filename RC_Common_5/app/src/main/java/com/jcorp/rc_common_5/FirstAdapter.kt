package com.jcorp.rc_common_5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_common_5.data.MainItem
import com.jcorp.rc_common_5.databinding.ItemMainBinding

class FirstAdapter : RecyclerView.Adapter<FirstAdapter.FViewHolder>() {
    private var item = mutableListOf<MainItem>()

    class FViewHolder(private val binding : ItemMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (item : MainItem) {
            binding.itemMainAlbumImg.setImageResource(item.albumImg)
            binding.itemMainTitle.text = item.conceptTitle
            binding.itemMainAlbumMainTxt.text = item.title
            binding.itemMainAlbumSubTxt.text = item.subTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainBinding>(layoutInflater, R.layout.item_main, parent, false)

        return FViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun setItem (mItem : MutableList<MainItem>) {
        item = mItem
        notifyDataSetChanged()
    }

}