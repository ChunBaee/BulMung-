package com.solie.ev_nyang

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.pedro.library.AutoPermissions
import com.pedro.library.AutoPermissionsListener
import com.solie.ev_map.dialog.StatusDialog
import com.solie.ev_nyang.databinding.ActivityMapBinding
import com.solie.ev_nyang.item.ClustItem
import com.solie.ev_nyang.item.EachStatItem
import com.solie.ev_nyang.item.FirebaseItem
import com.solie.ev_nyang.map.MarkerRenderer
import com.solie.ev_nyang.util.FirebaseData
import com.solie.ev_nyang.viewmodel.MarkerViewModel
import org.jetbrains.anko.locationManager

class MapActivity : AppCompatActivity(), FirebaseData, AutoPermissionsListener,
    ClusterManager.OnClusterItemClickListener<ClustItem> {
    private lateinit var binding: ActivityMapBinding

    private var mainList = mutableListOf<FirebaseItem>()

    private lateinit var clusterManager: ClusterManager<ClustItem>
    private lateinit var manager: LocationManager
    private lateinit var gpsListener: GPSListener

    private lateinit var curPoint : LatLng
    private var mapFrag: SupportMapFragment? = SupportMapFragment()
    private lateinit var mMap: GoogleMap
    private var mLocationMarker: MarkerOptions? = MarkerOptions()

    lateinit var model : MarkerViewModel
    /*private val observeListener = Observer<FirebaseItem> {
        it?.let {
            firebaseItem ->
            val dialog = StatusDialog(firebaseItem, curPoint)
            dialog.show(supportFragmentManager, "StatusDialog")
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        model = ViewModelProvider(this).get(MarkerViewModel::class.java)
        if(::model.isInitialized) {
            //model.MkInfo.observe(this, observeListener)
        }
        getBigInfo()
        manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsListener = GPSListener()
        try {
            MapsInitializer.initialize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mapFrag = supportFragmentManager.findFragmentById(R.id.MapFragment) as SupportMapFragment
        mapFrag?.getMapAsync(OnMapReadyCallback {
            mMap = it
            clusterManager = ClusterManager(applicationContext, mMap)
            mMap.setOnCameraIdleListener(clusterManager)
            mMap.setOnMarkerClickListener(clusterManager)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@OnMapReadyCallback
            }
            mMap.isMyLocationEnabled = true
            startLocationService()
        })
        setSpinners()
    }

    private fun getBigInfo() {
        firebaseStore.get()
            .addOnSuccessListener { result ->
                mainList.clear()
                for (document in result) {
                    mainList.add(document.toObject(FirebaseItem::class.java))
                }
                Log.d(TAG, "ZZZZZZ:Main ${mainList.size}")
                addStationMarker(mainList)
            }
    }

    private fun addStationMarker(mainList : MutableList<FirebaseItem>) {
        val list = mainList
        Log.d(TAG, "ZZZZZZ:Final ${list.size}")
        for (i in 0 until list.size) {
            clusterManager.renderer = MarkerRenderer(this, mMap, clusterManager)
            clusterManager.addItem(
                ClustItem(
                    list[i].lat,
                    list[i].lng,
                    list[i].statNm,
                    list[i].addr,
                    list[i].stat,
                    i
                )
            )
            clusterManager.setOnClusterItemClickListener(this)
        }
    }

    private fun startLocationService() {
        try {
            var location: Location? = null
            val minTime: Long = 0
            val minDistance = 0f

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                if (location != null) {
                    val latitude: Double = location.latitude
                    val longitude: Double = location.longitude
                    showCurrentLocation(latitude, longitude)
                }
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener
                )
            } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    showCurrentLocation(latitude, longitude)
                }
                manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener
                )
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun showCurrentLocation(latitude: Double, longitude: Double) {
        curPoint = LatLng(latitude, longitude)
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 14f))
            showMyLocationMarker(curPoint)
        } else {
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 14f))
            showMyLocationMarker(curPoint)
        }
    }

    private fun showMyLocationMarker(curPoint: LatLng) {
        mLocationMarker?.position(curPoint)
    }

    private fun setSpinners() {
        val statArray = resources.getStringArray(R.array.chgerStat)
        val statAdapter = ArrayAdapter<String>(this,R.layout.item_spinner,R.id.txt_Spinner,statArray)
        binding.spinnerStatus.adapter = statAdapter

        binding.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.spinnerStatus.getItemAtPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    inner class GPSListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude
            showCurrentLocation(latitude, longitude)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(applicationContext, "접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
        } else {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, gpsListener)
            } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, gpsListener)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        manager.removeUpdates(gpsListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this)
    }

    override fun onDenied(requestCode: Int, permissions: Array<String>) {
        TODO("Not yet implemented")
    }

    override fun onGranted(requestCode: Int, permissions: Array<String>) {
        TODO("Not yet implemented")
    }


    override fun onClusterItemClick(item: ClustItem?): Boolean {
        model.request(item!!.title.toString())
        return true
    }
}

