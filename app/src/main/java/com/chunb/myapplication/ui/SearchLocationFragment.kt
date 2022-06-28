package com.chunb.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.chunb.myapplication.ViewModel
import com.chunb.myapplication.adapter.LocationSearchAdapter
import com.chunb.myapplication.databinding.FragmentSearchLocationBinding

class SearchLocationFragment : Fragment() {
    private lateinit var binding : FragmentSearchLocationBinding
    private val viewModel by activityViewModels<ViewModel>()

    private val locationAdapter by lazy {
        LocationSearchAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchLocationBinding.inflate(inflater, container, false)

        setView()
        setRv()
        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.kakaoLocationSearchData.observe(requireActivity(), Observer {
            locationAdapter.setLocationList(it)
        })
    }

    private fun setView() {
        binding.searchEdtSearchByName.setOnKeyListener { view, i, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER && binding.searchEdtSearchByName.text.isNotEmpty()) {
                viewModel.getkakaoLocationData(binding.searchEdtSearchByName.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun setRv() {
        binding.searchLocationsRv.hasFixedSize()
        binding.searchLocationsRv.adapter = locationAdapter
        locationAdapter.searchClickedListener(object : LocationSearchAdapter.SearchClickedListener {
            override fun searchClickedListener(view: View, position: Int) {
                viewModel.setSearchedLocation(position)
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction()
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.useUserLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteKakaoLocationData()
    }
}