package com.jcorp.hwahae_clone.home.now.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowBeautyOnRvBinding
import com.jcorp.hwahae_clone.databinding.ItemHomeNowHotRvBinding
import com.jcorp.hwahae_clone.databinding.ItemHomeNowUnderAdvertiseHorizontalRvBinding
import com.jcorp.hwahae_clone.databinding.ItemHomeNowUnderAdvertiseVerticalRvBinding
import com.jcorp.hwahae_clone.home.now.data.*

class HomeNowUnderAdvertiseRvAdapter (val viewType : Int, val position : Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mPosition = position
    private var rankingList = mutableListOf<HomeNowHotItem>()
    private var shoppingList = mutableListOf<HomeNowShoppingItem>()
    private var familyList = mutableListOf<HomeNowVerticalItem>()
    private var mdList = mutableListOf<HomeNowVerticalItem>()
    private var beautyOnList = mutableListOf<HomeNowBeautyOnItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0 -> {
                HotRankingViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_home_now_hot_rv, parent, false))
            }
            1 -> {
                HorizontalProductInfoViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_home_now_under_advertise_horizontal_rv, parent, false))
            }
            3 -> {
                BeautyOnViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_home_now_beauty_on_rv, parent, false))
            }
            else -> {
                VerticalProductInfoViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_home_now_under_advertise_vertical_rv, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("----", "onBindViewHolder: $mPosition")
        when(viewType) {
            0 -> {
                (holder as HotRankingViewHolder).bind(rankingList[position])
            }
            1 -> {
                (holder as HorizontalProductInfoViewHolder).bind(shoppingList[position])
            }
            2 -> {
                when(mPosition) {
                    2 -> {
                        (holder as VerticalProductInfoViewHolder).bind(familyList[position])
                        Log.d("----", "onBindViewHolder: FAMILY")
                    }
                    3 -> {
                        (holder as VerticalProductInfoViewHolder).bind(mdList[position])
                        Log.d("----", "onBindViewHolder: MD")
                    }
                }
            }
            3 -> {
                (holder as BeautyOnViewHolder).bind(beautyOnList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return when(viewType) {
            0 -> rankingList.size
            1 -> shoppingList.size
            2 -> {
                when(mPosition) {
                    2 -> familyList.size
                    else -> mdList.size
                }
            }
            else -> beautyOnList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    inner class HotRankingViewHolder(val binding : ItemHomeNowHotRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowHotItem) {
            binding.hotItem = item
        }
    }

    inner class VerticalProductInfoViewHolder(val binding : ItemHomeNowUnderAdvertiseVerticalRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowVerticalItem) {
            binding.item = item
        }
    }

    inner class HorizontalProductInfoViewHolder(val binding : ItemHomeNowUnderAdvertiseHorizontalRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowShoppingItem) {
            binding.shoppingItem = item

            var oo = mutableListOf<Options>()
            for(i in item.optionsList) {
                oo.add(Options(i))
            }
            binding.itemHomeNowShoppingOptionsRv.adapter = HomeNowOptionsAdapter(oo)
        }
    }

    inner class BeautyOnViewHolder(val binding : ItemHomeNowBeautyOnRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowBeautyOnItem) {
            binding.beautyOnItem = item
        }
    }

    fun setHotList(list : MutableList<HomeNowHotItem>) {
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