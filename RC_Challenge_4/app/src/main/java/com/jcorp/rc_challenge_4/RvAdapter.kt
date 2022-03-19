package com.jcorp.rc_challenge_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_challenge_4.databinding.ItemRvBinding

class RvAdapter : RecyclerView.Adapter<RvAdapter.RvHolder>() {
    private var data = mutableListOf<RvItem>()
    private lateinit var onItemLongClickListener : OnItemLongClickListener

    interface OnItemLongClickListener {
        fun onItemLongClick(view : View, position : Int)
    }

    fun onItemLongClickListener (onItemLongClickListener : OnItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    fun removeData (position : Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class RvHolder(private val binding : ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : RvItem) {
            binding.imgChatlist.clipToOutline = true

            binding.imgChatlist.setImageResource(item.img)

            binding.txtChatlistTitle.text = item.title
            binding.txtChatlistContent.text = item.content
            binding.txtChatlistTime.text = item.time
            when(item.isGroup) {
                true -> {
                    binding.txtChatlistGroup.text = item.member.toString()
                }
                else -> {
                    binding.txtChatlistGroup.visibility = View.GONE
                }
            }

            binding.tvRemove.setOnClickListener{
                removeData(this.layoutPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRvBinding>(layoutInflater, R.layout.item_rv, parent, false)

        return RvHolder(binding)
    }

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener.onItemLongClick(it, position)
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList(list : MutableList<RvItem>) {
        data = list
        notifyDataSetChanged()
    }



}