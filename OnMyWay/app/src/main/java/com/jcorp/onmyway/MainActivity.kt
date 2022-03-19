package com.jcorp.onmyway

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.model.LatLng
import com.jcorp.onmyway.data.JsonData
import com.jcorp.onmyway.data.PostData
import com.jcorp.onmyway.data.Result
import com.jcorp.onmyway.databinding.ActivityMainBinding
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), MainActivityView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLoad.setOnClickListener {
            showLoadingDialog(this)
            MyService(this).tryGetData()
        }

        binding.btnPut.setOnClickListener {
            showLoadingDialog(this)
            MyService(this).tryPostData("PUT TEST")
        }

        getCurLocation()
    }

    override fun onGetDataSuccess(response: JsonData) {
        dismissLoadingDialog()

        for(data in response.result) {
            Log.d("----", "NO : ${data.no} / NAME : ${data.name} / REGISTERED : ${data.registeredTimestamp}")
        }
    }

    override fun onPostDataSuccess(response: Result) {
        dismissLoadingDialog()

            Log.d("----", "NO : ${response.no} / NAME : ${response.name} / REGISTERED : ${response.registeredTimestamp}")

    }
}