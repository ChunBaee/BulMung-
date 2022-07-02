package com.jcorp.hwahae_clone.home.now.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.ItemHomeNowUnderAdvertiseHorizontalRvBinding
import com.jcorp.hwahae_clone.home.now.data.HomeNowShoppingItem
import com.jcorp.hwahae_clone.home.now.data.Options

class HomeNowShoppingAdapter : RecyclerView.Adapter<HomeNowShoppingAdapter.HomeNowShoppingViewHolder>() {

    private var shoppingList = mutableListOf<HomeNowShoppingItem>()

    inner class HomeNowShoppingViewHolder (val binding : ItemHomeNowUnderAdvertiseHorizontalRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : HomeNowShoppingItem) {
            binding.shoppingItem = item

            var oo = mutableListOf<Options>()

            binding.itemHomeNowShoppingOptionsRv.hasFixedSize()
            for(i in item.optionsList) {
                oo.add(Options(i))
            }
            binding.itemHomeNowShoppingOptionsRv.adapter = HomeNowOptionsAdapter(oo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNowShoppingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHomeNowUnderAdvertiseHorizontalRvBinding>(layoutInflater, R.layout.item_home_now_under_advertise_horizontal_rv, parent, false)
        return HomeNowShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNowShoppingViewHolder, position: Int) {
        holder.bind(shoppingList[position])

        var oo = mutableListOf<Options>()

        holder.binding.itemHomeNowShoppingOptionsRv.hasFixedSize()
        for(i in shoppingList[position].optionsList) {
            oo.add(Options(i))
        }
        holder.binding.itemHomeNowShoppingOptionsRv.adapter = HomeNowOptionsAdapter(oo)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }


}