package com.jcorp.hwahae_clone.home.now

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import com.google.android.material.tabs.TabLayoutMediator
import com.jcorp.hwahae_clone.R
import com.jcorp.hwahae_clone.databinding.FragmentHomeNowBinding
import com.jcorp.hwahae_clone.home.now.adapter.*
import com.jcorp.hwahae_clone.onInfiniteCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeNowFragment : Fragment(), onInfiniteCallback{
    private lateinit var binding : FragmentHomeNowBinding
    private val topRvAdapter : HomeNowTopAdapter by lazy {
        HomeNowTopAdapter(requireActivity())
    }
    private val newRvAdapter: HomeNowNewAdapter by lazy {
        HomeNowNewAdapter(requireActivity())
    }
    private val adPagerAdapter : HomeNowAdAdapter by lazy {
        HomeNowAdAdapter()
    }
    private val underAdRvTitleAdapter : HomeNowUnderAdvertiseAdapter by lazy {
        HomeNowUnderAdvertiseAdapter(requireActivity())
    }

    private val viewModel by activityViewModels<HomeNowViewModel>()

    private var isRunning = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeNowBinding.inflate(inflater, container, false)

        observe()
        setView()

        return binding.root
    }

    private fun observe() {
        viewModel.homeNowCategoryData.observe(requireActivity(), Observer {
            topRvAdapter.setList(it)
        })
        viewModel.homeNowNewProductData.observe(requireActivity(), Observer {
            newRvAdapter.setNewItemList(it)
        })
        viewModel.homeNowAdvertiseData.observe(requireActivity(), Observer {
            adPagerAdapter.setAdList(it)
            onInfinitePagerCallback(binding.homeNowPagerAdvertise, it.size + 2)
        })
        viewModel.homeNowUnderAdvertiseTitleData.observe(requireActivity(), Observer {
            underAdRvTitleAdapter.setTitleList(it)
        })
        viewModel.homeNowHotRankingData.observe(requireActivity(), Observer {
            underAdRvTitleAdapter.setRankingList(it)
        })
        viewModel.homeNowShoppingData.observe(requireActivity(), Observer {
            underAdRvTitleAdapter.setShoppingList(it)
        })
        viewModel.familyMonthData.observe(requireActivity(), Observer {
            underAdRvTitleAdapter.setFamilyList(it)
        })
        viewModel.mdRecommendData.observe(requireActivity(), Observer {
            underAdRvTitleAdapter.setMdRecommendList(it)
        })
        viewModel.beautyOnData.observe(requireActivity(), Observer {
            underAdRvTitleAdapter.setBeautyOnList(it)
        })
    }

    private fun setView() {
        setTopRv()
        setNewRv()
        setAdPager()
        setUnderAdRv()
    }


    private fun setTopRv() {
        binding.homeNowRvCategory.hasFixedSize()
        binding.homeNowRvCategory.adapter = topRvAdapter
        viewModel.setHomeNowCategoryData()
    }

    private fun setNewRv() {
        binding.homeNowRvNewItem.hasFixedSize()
        binding.homeNowRvNewItem.adapter = newRvAdapter
        viewModel.setHomeNowNewProductData()
    }

    private fun setAdPager() {
        binding.homeNowPagerAdvertise.adapter = adPagerAdapter
        viewModel.setHomeNowAdvertiseData()

        binding.homeNowPagerAdvertise.getChildAt(0).setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            return@setOnTouchListener false
        }
        TabLayoutMediator(binding.homeNowAdvertiseIndicator, binding.homeNowPagerAdvertise) { tab, position ->

        }.attach()
        adAutoScroll()
    }

    private fun adAutoScroll() {
        lifecycleScope.launch {
            whenResumed {
                while(isRunning) {
                    delay(3000)
                    var pastItem = binding.homeNowPagerAdvertise.currentItem
                    binding.homeNowPagerAdvertise.setCurrentItem(pastItem + 1, true)
                }
            }
        }
    }

    private fun setUnderAdRv() {
        binding.homeNowRvUnderAdvertise.hasFixedSize()
        binding.homeNowRvUnderAdvertise.adapter = underAdRvTitleAdapter
        viewModel.setHomeNowUnderAdvertiseTitleData()
        viewModel.setHomeNowHotRankingData()
        viewModel.setHomeNowShoppingData()
        viewModel.setFamilyMonthData()
        viewModel.setMdRecommendData()
        viewModel.setBeautyOnData()
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }
}