package com.jcorp.onmyway

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.model.LatLng
import java.util.*

abstract class BaseActivity<B : ViewBinding>(private val inflate : (LayoutInflater) -> B) : AppCompatActivity() {

    protected lateinit var binding : B
        private set
    lateinit var mLoadingDialog : LoadingDialog

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showLoadingDialog(context : Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    fun dismissLoadingDialog() {
        if(mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    fun getCurLocation() {

        val locationListener = object : android.location.LocationListener {
            override fun onLocationChanged(p0: Location) {
                Log.d("----", "onLocationChanged: ${getAddress(LatLng(p0.latitude, p0.longitude))}")
                Toast.makeText(applicationContext, getAddress(LatLng(p0.latitude, p0.longitude)), Toast.LENGTH_SHORT).show()

            }

        }

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
        locationManager.requestLocationUpdates (
            LocationManager.GPS_PROVIDER,
            1000L,
            10F,
            locationListener
                )
    }

    private fun getAddress(position: LatLng): String {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val a = geoCoder.getFromLocation(position.latitude, position.longitude, 1)[0]
        return "${a.adminArea} ${a.locality} ${a.thoroughfare}"



    }
}

