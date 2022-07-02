package com.chunb.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.chunb.myapplication.R
import com.chunb.myapplication.ViewModel
import com.chunb.myapplication.data.CampingListData
import com.chunb.myapplication.data.Item
import com.chunb.myapplication.databinding.FragmentMapBinding
import com.chunb.myapplication.util.ThemeManager
import com.kakao.sdk.navi.NaviClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding

    private lateinit var nMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private val viewModel by activityViewModels<ViewModel>()

    private val LOCATION_REQUEST_CODE = 1000

    private var markerList = mutableListOf<Marker>()

    private val infoWindow = InfoWindow()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        locationSource = FusedLocationSource(this, LOCATION_REQUEST_CODE)
        var mapFragment: MapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observe() {
        //현재좌표 쓰는지 아닌지 여부
        viewModel.isCurLocation.observe(this, Observer {
            Log.d("----", "setNo: isCurLocation : ${viewModel.isCurLocation.value}")
            when (it) {
                true -> setMapsLocationSource()
                else -> setNoLocationSource()
            }
        })

        viewModel.locationBasedCampingData.observe(this, Observer {
            Log.d("----", "basedObserve: $it")
            Log.d("----", "basedObserve: ${it.size}")
            for (i in 0 until markerList.size) {
                markerList[i].map = null
            }
            markerList = mutableListOf()
            setMarker(it)
        })

        viewModel.mapSeekRange.observe(this, Observer {
            Log.d("----", "observe: RANGE $it")
            viewModel.getLocationData()
        })
    }

    private fun setMarker(list: MutableList<Item>) {
        for (i in 0 until list.size) {
            val marker = Marker()
            marker.width = 70
            marker.height = 80
            marker.tag = list[i].facltNm
            marker.position = LatLng(list[i].mapY, list[i].mapX)
            Log.d("----", "setMarker: ${marker.position}")
            markerList.add(marker)
            markerList[i].map = nMap

            marker.setOnClickListener {
                if (marker.infoWindow == null) {
                    infoWindow.open(marker)
                } else {
                    viewModel.setCurCampingData(i)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.home_main_keyword_container, CampingDetailFragment())
                        .addToBackStack("Detail").commit()
                }
                true
            }
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                override fun getText(p0: InfoWindow): CharSequence {
                    return infoWindow.marker?.tag as CharSequence? ?: ""
                }
            }
        }
    }

    private fun setMapsLocationSource() {
        Log.d("----", "setNoLocationSource: YesLocation")
        nMap.locationSource = locationSource
        nMap.locationTrackingMode = LocationTrackingMode.Follow

        nMap.addOnLocationChangeListener {
            viewModel.setMapLocation(it.latitude, it.longitude)
            viewModel.getLocationData()
        }
    }

    private fun setNoLocationSource() {
        Log.d("----", "setNoLocationSource: NoLocation")
        Log.d("----", "setNoLocationSource: ${viewModel.getMapLocation()}")
        nMap.locationSource = null
        nMap.locationTrackingMode = LocationTrackingMode.NoFollow
        if (viewModel.isKeyword.value == true) {
        } else {
            nMap.moveCamera(CameraUpdate.scrollTo(viewModel.getMapLocation()))
            viewModel.getLocationData()
        }
    }

    private fun mapClickEvent() {
        nMap.setOnMapClickListener { _, _ ->
            infoWindow.close()
        }
    }

    override fun onMapReady(p0: NaverMap) {
        this.nMap = p0
        Log.d("----", "onMapReady: $nMap")
        //nMap.uiSettings.isLocationButtonEnabled = true
        observe()
        mapClickEvent()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                nMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}