package com.chunb.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chunb.myapplication.R
import com.chunb.myapplication.data.Place
import com.chunb.myapplication.databinding.ItemLocationListRvBinding

class LocationSearchAdapter : RecyclerView.Adapter<LocationSearchAdapter.LocationSearchViewHolder>(){
    private var locationList = mutableListOf<Place>()
    private lateinit var searchClickedListener : SearchClickedListener


    interface SearchClickedListener {
        fun searchClickedListener (view : View, position : Int)
    }

    fun searchClickedListener(clickListener : SearchClickedListener) {
        this.searchClickedListener = clickListener
    }

    inner class LocationSearchViewHolder(val binding : ItemLocationListRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Place) {
            binding.locationItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemLocationListRvBinding>(layoutInflater, R.layout.item_location_list_rv, parent, false)
        return LocationSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationSearchViewHolder, position: Int) {
        holder.bind(locationList[position])

        holder.itemView.setOnClickListener {
           searchClickedListener.searchClickedListener(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    fun setLocationList(list : MutableList<Place>) {
        locationList = list
        notifyDataSetChanged()
    }
}