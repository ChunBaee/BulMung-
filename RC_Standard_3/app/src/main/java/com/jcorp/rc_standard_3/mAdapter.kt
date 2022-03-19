package com.jcorp.rc_standard_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_standard_3.databinding.ListItemBinding

class mAdapter : RecyclerView.Adapter<mAdapter.mViewHolder>(){

    private var data = mutableListOf<mItem>()

    class mViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (mItem : mItem) {
            binding.itemImg.setImageResource(mItem.Img)
            binding.itemTitle.text = mItem.title
            binding.itemLocation.text = mItem.location
            binding.itemTime.text = mItem.time
            binding.txtChat.text = mItem.chatCount.toString()
            binding.txtHeart.text = mItem.heartCount.toString()
            if(mItem.time == null) {
                binding.itemDivider.visibility = View.INVISIBLE
            }
            binding.itemPrice.text = mItem.price
            if(!mItem.heartTF) {
               binding.imgHeart.visibility = View.INVISIBLE
                binding.txtHeart.visibility = View.INVISIBLE
            }
            if(!mItem.chatTF) {
                binding.imgChat.visibility = View.INVISIBLE
                binding.txtChat.visibility = View.INVISIBLE
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemBinding>(layoutInflater, R.layout.list_item, parent, false)

        return mViewHolder(binding)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.itemTitle.text = data[position].title
        holder.binding.itemImg.setImageResource(data[position].Img)
        holder.binding.itemLocation.text = data[position].location
        holder.binding.itemTime.text = data[position].time
        holder.binding.itemPrice.text = data[position].price
        holder.binding.txtHeart.text = data[position].heartCount.toString()
        holder.binding.txtChat.text = data[position].chatCount.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setContent(list : MutableList<mItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }

}