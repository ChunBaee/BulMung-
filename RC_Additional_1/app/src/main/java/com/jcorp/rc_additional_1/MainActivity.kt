package com.jcorp.rc_additional_1

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jcorp.rc_additional_1.databinding.ActivityMainBinding
import com.jcorp.rc_additional_1.retrofit.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mapFrag : MapFragment
    private lateinit var nMap : NaverMap
    private lateinit var locationSource : FusedLocationSource
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000

    private lateinit var marker : Marker

    private val viewModel by viewModels<mViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mapFrag = supportFragmentManager.findFragmentById(R.id.mapFrag) as MapFragment
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapFrag.getMapAsync(this)

        //GEO.getGeo("산격동 1185-19")

        binding.lifecycleOwner = this

        observe()

        binding.GEO.setOnClickListener {
            RevGEO(viewModel.orderXY.value!!)


        }

        binding.btnLocation.setOnClickListener {
            GEO(binding.edtLocation.text.toString())
            Log.d("----", "onCreate: ${binding.edtLocation.text}")


        }

    }

    private fun observe() {
        viewModel.isSearch.observe(this, Observer {
            when(it) {
                false -> {
                    marker.map = null
                    nMap.setLocationSource(locationSource)
                    nMap.uiSettings.isLocationButtonEnabled = true
                    nMap.locationTrackingMode = LocationTrackingMode.Follow
                    nMap.addOnLocationChangeListener { location ->
                        Log.d("////", "LAT : ${location.latitude}, LNG : ${location.longitude}")
                        viewModel.setCurXY(location.longitude, location.latitude)
                    }
                }

                true -> {
                    marker.position = LatLng(viewModel.searchX.value!!, viewModel.searchY.value!!)
                    Log.d("----", "observe: ${viewModel.searchX.value}, ${viewModel.searchY.value}")
                    marker.map = nMap

                    nMap.moveCamera(CameraUpdate.scrollTo(marker.position))
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if(!locationSource.isActivated) {
                nMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(p0: NaverMap) {
        this.nMap = p0
        marker = Marker()

        marker.map = null
        nMap.setLocationSource(locationSource)
        nMap.uiSettings.isLocationButtonEnabled = true
        nMap.locationTrackingMode = LocationTrackingMode.Follow
        nMap.addOnLocationChangeListener { location ->
            Log.d("////", "LAT : ${location.latitude}, LNG : ${location.longitude}")
            viewModel.setCurXY(location.longitude, location.latitude)
        }
    }


    private fun RevGEO(coords : String) {
            RetrofitManager.instance.getRevGeo(coords, completion = {revGeoData: RevGeoData? ->
                binding.edtLocation.setText("${revGeoData?.results?.get(0)?.region?.area1?.name} ${revGeoData?.results?.get(0)?.region?.area2?.name} ${revGeoData?.results?.get(0)?.region?.area3?.name} ${revGeoData?.results?.get(0)?.land?.number1} - ${revGeoData?.results?.get(0)?.land?.number2}")
            })
        viewModel.isSearchOrNot(false)

    }

    private fun GEO(query: String) {
        RetrofitManager.instance.getGeo(query, completion = { geoData: GeoData? ->
            viewModel.searchX.value = geoData?.addresses?.get(0)?.y
            viewModel.searchY.value = geoData?.addresses?.get(0)?.x

            Log.d("----", "getGeo: ${geoData?.addresses?.get(0)?.x}, ${geoData?.addresses?.get(0)?.y}")
            viewModel.isSearchOrNot(true)
        })
    }
}
