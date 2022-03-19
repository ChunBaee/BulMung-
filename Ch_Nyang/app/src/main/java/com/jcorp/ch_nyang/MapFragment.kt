package com.jcorp.ch_nyang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.jcorp.ch_nyang.databinding.FragmentMapBinding
import com.jcorp.ch_nyang.retrofit.ChargerInfo
import com.jcorp.ch_nyang.retrofit.RetrofitManager

class MapFragment : Fragment() {
    private lateinit var binding : FragmentMapBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mMap : GoogleMap
    private var mMapReady = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mMapReady = true
            updateMap()
        }
        binding.activity = this
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    private fun updateMap() {

    }

    fun setFB(view : View) {
        ChargerInfo.getInfo(1)
    }

}