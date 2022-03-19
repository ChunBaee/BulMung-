package com.jcorp.rc_additional_3.directmsg.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_additional_3.R
import com.jcorp.rc_additional_3.databinding.ItemDmListBinding
import com.jcorp.rc_additional_3.directmsg.data.ItemDmList

class DmListAdapter(context : Context) : RecyclerView.Adapter<DmListAdapter.DmViewHolder>() {
    private var data = mutableListOf<ItemDmList>()
    private val mContext = context
    private lateinit var anim : Animation

    class DmViewHolder( val binding : ItemDmListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ItemDmList) {
            binding.imgDmItem.setImageResource(item.IconRes!!)
            binding.imgDmItem.setBackgroundResource(item.radi!!)
            binding.animatorDmItem.setBackgroundResource(item.anim!!)

            /*if(item.anim == true) {
                binding.animatorDmItem.visibility = View.VISIBLE
                binding.animatorDmItem.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dm_list_sidebar_selected))
            }else if(item.anim == false){
                //binding.animatorDmItem.visibility = View.INVISIBLE
                binding.animatorDmItem.startAnimation(
                    AnimationUtils.loadAnimation(mContext, R.anim.dm_list_sidebar_unselected)
                )
            }*/
            binding.imgDmItem.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DmViewHolder {
        anim = AnimationUtils.loadAnimation(parent.context, R.anim.dm_list_sidebar_selected)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDmListBinding>(layoutInflater, R.layout.item_dm_list, parent, false)
        return DmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DmViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.imgDmItem.setImageResource(data[position].IconRes!!)
        holder.binding.imgDmItem.setBackgroundResource(data[position].radi!!)
        holder.itemView.clipToOutline = true

        holder.itemView.setOnClickListener {
            for(i in 0 until data.size) {
                data[i].radi = R.drawable.btn_dm_list_unclicked
                data[i].anim = R.drawable.dm_list_sidebar_unclicked
            }
            data[holder.adapterPosition].radi = R.drawable.btn_dm_list_clicked
            data[holder.adapterPosition].anim = R.drawable.dm_list_sidebar_clicked
            notifyDataSetChanged()

            Log.d("----", "onBindViewHolder: $data")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(list : MutableList<ItemDmList>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}