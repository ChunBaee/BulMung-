package com.jcorp.hwahae_clone

import android.util.Log
import androidx.viewpager2.widget.ViewPager2

interface onInfiniteCallback {
    fun onInfinitePagerCallback(view : ViewPager2, listSize : Int) {
        view.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if(state == ViewPager2.SCROLL_STATE_IDLE) {
                    when(view.currentItem) {
                        listSize - 1 -> {
                            view.setCurrentItem(1, false)
                        }
                        0 -> {
                            view.setCurrentItem(listSize-2, false)
                        }
                    }
                }
            }
        })
    }
}