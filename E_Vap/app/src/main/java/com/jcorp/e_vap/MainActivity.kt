package com.jcorp.e_vap

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import com.google.maps.android.clustering.ClusterManager
import com.jcorp.e_vap.databinding.ActivityMainBinding
import com.jcorp.e_vap.dialog.ClusterRenderer
import com.jcorp.e_vap.model.MapItem
import com.jcorp.e_vap.retrofit.StatusInfo
import com.jcorp.e_vap.viewmodel.*
import com.navdrawer.SimpleSideDrawer
import com.solie.ev_map.dialog.StatusDialog
import kotlinx.coroutines.selects.select
import kotlin.math.log

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    ClusterManager.OnClusterItemClickListener<MapItem> {

    private lateinit var binding: ActivityMainBinding
    private lateinit var locationViewModel: LocationViewModel

    lateinit var mapViewModelFactory: MapViewModelFactory
    private val mapViewModel by viewModels<MapViewModel> { mapViewModelFactory }

    lateinit var mMap: GoogleMap

    private lateinit var dialog: StatusDialog

    private var mLocationMarker: MarkerOptions? = MarkerOptions()

    private lateinit var curPoint: LatLng

    private var setCamAtMyLocation = true

    lateinit var clusterManager: ClusterManager<MapItem>

    private var sideBool = false

    private val LOCATION_PERMISSION_REQUEST_CODE = 2000

    private lateinit var navController : NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    var checkFragSetNum = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.activity = this

        val mapFrag =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFrag.getMapAsync(this)

        prepRequestLocationUpdates()
        getMarkerInfo()
        setTab()
        setSpinners()
        getTabFragResult()
    }

    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun requestLocationUpdates() {
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(this, Observer {
            Log.d(TAG, "requestLocationUpdates: lat : ${it.latitude.toDouble()} // lng : ${it.longitude.toDouble()}")
            curPoint = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
            when (setCamAtMyLocation) {
                true -> mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.latitude.toDouble(),
                            it.longitude.toDouble()
                        ), 14f
                    )
                )
            }
            mLocationMarker?.position(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
            Log.d(TAG, "requestLocationUpdates: ${it.latitude}, ${it.longitude}")
        })
    }

    private fun getMarkerInfo() {
        mapViewModelFactory = MapViewModelFactory(0,0)
        //mapViewModel = ViewModelProvider(this, mapViewModelFactory).get(MapViewModel::class.java)
        mapViewModel.getMapLiveData().observe(this, Observer {
            if(it != null) {
                clusterManager.addItem(
                    MapItem(
                        it.addr,
                        it.busiNm,
                        it.lat,
                        it.lng,
                        it.powerType,
                        it.statNm,
                        it.useTime,
                        it.chgerType,
                        it.limitYn,
                        it.limitDetail,
                        it.parkingFree,
                        it.stat,
                    )
                )
            }
            Log.d(TAG, "getMarkerInfo: ${it.statNm} / ${it.stat} // CHGERSTATLIST")
            Log.d(TAG, "getMarkerInfo: ${it.getStatus()} // CHGERSTAT")
        })
    }

    private fun setTab() {
        val tabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("충전기 타입별"))
        tabLayout.addTab(tabLayout.newTab().setText("충전기 상태별"))

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.tabContainer.visibility = View.VISIBLE
                val position = tab.position
                when (position) {
                    0 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.tabContainer,
                            TabFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("position", position)
                                }
                            }).commit()
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.tabContainer,
                            TabFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("position", position)
                                }
                            }
                        ).commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                binding.tabContainer.visibility = View.GONE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (binding.tabContainer.visibility) {
                    View.VISIBLE -> binding.tabContainer.visibility = View.GONE

                    View.GONE -> binding.tabContainer.visibility = View.VISIBLE
                }

            }

        })
    }

    private fun setSpinners() {
       /* val infoArray = resources.getStringArray(R.array.appInfoList)
        val infoAdapter = ArrayAdapter(this, R.layout.item_spinner, R.id.txt_Spinner, infoArray)
        binding.sideView.serviceNotice.adapter = infoAdapter
        binding.sideView.serviceNotice.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.sideView.serviceNotice.getItemAtPosition(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            } */
    }

    private fun getTabFragResult() {
        checkFragSetNum.observe(this, Observer {
            Log.d(TAG, "getTabFragResult: $checkFragSetNum // Changed")
        })
    }

    fun onFBSetClick(view: View) {
        //StatusInfo.getStatus(1)
        when (setCamAtMyLocation) {
            true -> {
                setCamAtMyLocation = false
                binding.LockBtn.setImageResource(R.drawable.icon_lock)
            }

            false -> {
                setCamAtMyLocation = true
                binding.LockBtn.setImageResource(R.drawable.icon_unlock)
            }
        }
    }

    fun onSideBtnClick(view: View) {
        val sideView: View = findViewById(R.id.sideView)
        when (sideBool) {
            true -> binding.drawerLayout.closeDrawer(sideView)

            false -> binding.drawerLayout.openDrawer(sideView)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        clusterManager = ClusterManager(applicationContext, mMap)
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        clusterManager.renderer = ClusterRenderer(this, mMap, clusterManager)
        clusterManager.setOnClusterItemClickListener(this)
        mMap.isMyLocationEnabled = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "권한이 없어 현재 위치를 불러올 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onClusterItemClick(item: MapItem): Boolean {
        var mItem = MutableLiveData<MapItem>()
        mItem.value = item
        mItem.observe(this, Observer {
            dialog = StatusDialog(it, curPoint)
            dialog.show(supportFragmentManager, "StatusDialog")
        })

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}