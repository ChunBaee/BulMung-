package com.jcorp.hwahae_clone.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jcorp.hwahae_clone.home.beautyon.HomeBeautyOnFragment
import com.jcorp.hwahae_clone.home.event.HomeEventFragment
import com.jcorp.hwahae_clone.home.now.HomeNowFragment
import com.jcorp.hwahae_clone.home.ranking.HomeRankingFragment

class HomePagerAdapter (fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeNowFragment()
            1 -> HomeBeautyOnFragment()
            2 -> HomeEventFragment()
            3 -> HomeRankingFragment()
            else -> HomeNowFragment()
        }
    }

}