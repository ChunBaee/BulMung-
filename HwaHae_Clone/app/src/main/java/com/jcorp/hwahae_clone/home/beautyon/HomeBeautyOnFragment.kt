package com.jcorp.hwahae_clone.home.beautyon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import com.google.android.material.tabs.TabLayoutMediator
import com.jcorp.hwahae_clone.databinding.FragmentHomeBeautyOnBinding
import com.jcorp.hwahae_clone.home.beautyon.adapter.HomeBeautyOnBannerAdapter
import com.jcorp.hwahae_clone.home.beautyon.adapter.HomeBeautyOnContentAdapter
import com.jcorp.hwahae_clone.onInfiniteCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeBeautyOnFragment : Fragment(), onInfiniteCallback {
    private lateinit var binding : FragmentHomeBeautyOnBinding
    private val viewModel by activityViewModels<HomeBeautyOnViewModel>()

    private var originalLocation : Int = 0
    private var isRunning = true

    private val bannerAdapter by lazy {
        HomeBeautyOnBannerAdapter()
    }
    private val contentAdapter by lazy {
        HomeBeautyOnContentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBeautyOnBinding.inflate(inflater, container, false)

        observe()
        setBanner()
        setRv()
        setStickyHeader()

        return binding.root
    }

    private fun observe() {
        viewModel.homeBeautyOnBannerItem.observe(requireActivity(), Observer {
            bannerAdapter.setBannerList(it)
            onInfinitePagerCallback(binding.homeBeautyOnTopPager, it.size + 2)
        })
        viewModel.homeBeautyOnContentItem.observe(requireActivity(), Observer {
            contentAdapter.setContentList(it)
        })
        viewModel.curScroll.observe(requireActivity(), Observer {
            var location = IntArray(2)
            binding.homeBeautyOnBtnEditorSelect.getLocationOnScreen(location)
            Log.d("----", "aaa: Location1 : ${location[1]}")
            Log.d("----", "aaa: contentCurTop : ${binding.homeBeautyOnBtnEditorSelect.top}")
            Log.d("----", "aaa: CUR : ${viewModel.mainTabCurHeight.value} // TAB : ${viewModel.mainTabHeight} //i2 $it //i4 ${viewModel.pastScroll.value}")

            if(it > binding.homeBeautyOnBtnEditorSelect.top) {
                binding.homeBeautyOnBtnEditorSelect.translationY = (it - binding.homeBeautyOnBtnEditorSelect.top).toFloat()
            }
        })
    }

    private fun setBanner() {
        viewModel.setHomeBeautyOnBannerItem()
        binding.homeBeautyOnTopPager.adapter = bannerAdapter
        binding.homeBeautyOnTopPager.getChildAt(0).setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            return@setOnTouchListener false
        }
        TabLayoutMediator(binding.homeBeautyOnTopPagerIndicator, binding.homeBeautyOnTopPager) { tab, position ->

        }.attach()
        adAutoScroll()
    }

    private fun setRv() {
        binding.homeBeautyOnRvContent.hasFixedSize()
        binding.homeBeautyOnRvContent.adapter = contentAdapter
        viewModel.setHomeBeautyOnContentItem()
    }

    private fun setStickyHeader() {
        binding.homeBeautyOnBtnEditorSelect.translationZ = 1.0F
    }

    private fun adAutoScroll() {
        lifecycleScope.launch {
            whenResumed {
                while (isRunning) {
                    delay(3000)
                    var pastItem = binding.homeBeautyOnTopPager.currentItem
                    binding.homeBeautyOnTopPager.setCurrentItem(pastItem + 1, true)
                }
            }
        }
    }

    override fun onResume() {
        var location = IntArray(2)
        binding.homeBeautyOnBtnEditorSelect.getLocationOnScreen(location)
        originalLocation = location[1]
        Log.d("----", "aaa: ${location[1]}")

        Log.d("----", "aaa: HEI : ${binding.homeBeautyOnBtnEditorSelect.bottom - binding.homeBeautyOnBtnEditorSelect.top}")
        super.onResume()
    }
}