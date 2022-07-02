package com.jcorp.hwahae_clone.home.now.adapter

import android.app.ActionBar
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowUnderAdvertiseRvBinding
import com.jcorp.hwahae_clone.home.now.data.*

class HomeNowUnderAdvertiseAdapter(context : Context) : RecyclerView.Adapter<HomeNowUnderAdvertiseAdapter.HomeNowUnderAdvertiseViewHolder>() {
    private val mContext = context
    private var underAdTitleList = mutableListOf<Titles>()
    private var rankingList = mutableListOf<HomeNowHotItem>()
    private var shoppingList = mutableListOf<HomeNowShoppingItem>()
    private var familyList = mutableListOf<HomeNowVerticalItem>()
    private var mdList = mutableListOf<HomeNowVerticalItem>()
    private var beautyOnList = mutableListOf<HomeNowBeautyOnItem>()

    inner class HomeNowUnderAdvertiseViewHolder (val binding : ItemHomeNowUnderAdvertiseRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Titles) {
            binding.underAdItem = item
            var adapter = HomeNowUnderAdvertiseRvAdapter(item.viewType, adapterPosition)
            when(item.viewType) {
                0 -> {
                    binding.homeNowRvUnderAdvertise.layoutManager = GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false)
                    //context 받아오기 LinearLayoutManager(context, orientation, false)
                }
                1 -> {
                    binding.homeNowRvUnderAdvertise.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                }
                2 -> {
                    binding.homeNowRvUnderAdvertise.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                }
                3 -> {
                    binding.homeNowRvUnderAdvertise.layoutManager = GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false)
                }
            }
            binding.homeNowRvUnderAdvertise.adapter = adapter

            adapter.setHotList(rankingList)
            adapter.setShoppingList(shoppingList)
            adapter.setFamilyList(familyList)
            adapter.setMdRecommendList(mdList)
            adapter.setBeautyOnList(beautyOnList)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNowUnderAdvertiseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeNowUnderAdvertiseRvBinding>(layoutInflater, R.layout.item_home_now_under_advertise_rv, parent, false)
        return HomeNowUnderAdvertiseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowUnderAdvertiseViewHolder, position: Int) {
        holder.bind(underAdTitleList[position])
    }

    override fun getItemCount(): Int {
        return underAdTitleList.size
    }

    fun setTitleList(list : MutableList<Titles>) {
        underAdTitleList = list
        notifyDataSetChanged()
    }

    fun setRankingList (list : MutableList<HomeNowHotItem>) {
        rankingList = list
        notifyDataSetChanged()
    }

    fun setShoppingList (list : MutableList<HomeNowShoppingItem>) {
        shoppingList = list
        notifyDataSetChanged()
    }

    fun setFamilyList (list : MutableList<HomeNowVerticalItem>) {
        familyList = list
        notifyDataSetChanged()
    }

    fun setMdRecommendList (list : MutableList<HomeNowVerticalItem>) {
        mdList = list
        notifyDataSetChanged()
    }

    fun setBeautyOnList (list : MutableList<HomeNowBeautyOnItem>) {
        beautyOnList = list
        notifyDataSetChanged()
    }
}