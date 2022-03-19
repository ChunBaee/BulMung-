package com.jcorp.experience

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.jcorp.experience.databinding.ActivityMainBinding
import com.jcorp.experience.model.WeatherInfoItem
import com.jcorp.experience.retrofit.WeatherAPI
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()

    private val LOCATION_PERMISSION_REQUEST_CODE = 2000

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private var nowDay = ""
    private var nowTime = ""
    private var standardTime = ""

    private var requestLatitude = 0
    private var requestLongitude = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        viewModel.observeTimeMills()
        getLocation()

        viewModel.curLocation.observe(this, androidx.lifecycle.Observer {
            Log.d(TAG, "onCreate: ${it.latitude} // ${it.longitude}")
        })

        viewModel.curTime.observe(this, androidx.lifecycle.Observer {
            nowDay = SimpleDateFormat("yyyyMMdd").format(it)
            nowTime = SimpleDateFormat("HH").format(it)
        })
        prepRequestLocationUpdates()
        getWeatherData()

        binding.btnRefresh.setOnClickListener {
            viewModel.observeTimeMills()
        }

        binding.btnApiRequest.setOnClickListener {
            Log.d(TAG, "onCreate: // ${nowTime.toInt()}")
            when ((nowTime.toInt() * 60) % 180) {
                0 -> {
                    nowTime = if (nowTime.toInt() != 0) {
                        ((nowTime.toInt() - 1) * 100).toString()
                    } else {
                        "2300"
                    }
                }
                60 -> {
                    nowTime = if (nowTime.toInt() != 1) {
                        ((nowTime.toInt() - 2) * 100).toString()
                    } else {
                        "2300"
                    }
                }
                else -> nowTime = (nowTime.toInt() * 100).toString()
            }
            WeatherAPI.getWeather(
                nx = requestLatitude,
                ny = requestLongitude,
                baseDate = nowDay.toInt(),
                baseTime = nowTime.toInt()
            )
        }
    }

    //위치 권한 받아오기
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

    //현재위치 주소 받아오기
    private fun requestLocationUpdates() {
        val mGeoCoder = Geocoder(this)
        viewModel.curLocation.observe(this, androidx.lifecycle.Observer {
            requestLongitude = it.longitude.toDouble().roundToInt()
            requestLatitude = it.latitude.toDouble().roundToInt()
            Log.d(TAG, "requestLocationUpdates: $requestLongitude $requestLatitude")

            val mAddress =
                mGeoCoder.getFromLocation(it.longitude.toDouble(), it.latitude.toDouble(), 5)
            val mCurCity = mAddress[0].adminArea
            val mCurSubLocation = mAddress[0].subLocality
            val mCurTown = mAddress[0].thoroughfare

            if (mCurCity != null && mCurSubLocation != null && mCurTown != null) {
                binding.standardLocation.text = "$mCurCity $mCurSubLocation $mCurTown"
            }
        })


    }

    // 현재위치 받아오기
    private fun getLocation() {
        val locationListener = LocationListener { location ->
            location.let {
                viewModel.getCurLocation(it.latitude, it.longitude)
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
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            0.5f,
            locationListener
        )
    }

    //Firebase에서 날씨정보 받아오기
    private fun getWeatherData() {
        FirebaseFirestore.getInstance()
            .collection("WeatherDB")
            .document("$requestLongitude _ $requestLatitude")
            .collection(SimpleDateFormat("yyyyMMdd").format(viewModel.curTime.value!!))
            .get()
            .addOnSuccessListener { task ->
                task.forEach {
                    if (nowTime == it.id) {

                        binding.txtStandardTime.text = "기준시간 : " +
                                "${SimpleDateFormat("yyyy").format(it)}년 " +
                                "${SimpleDateFormat("MM").format(it)}월 " +
                                "${SimpleDateFormat("dd").format(it)}일 " +
                                "${it.id}시"

                        setView(it.toObject(WeatherInfoItem::class.java))
                    }
                }
            }
    }

    private fun setView(item: WeatherInfoItem) {
        val mItem = item

        when(mItem.SKY.toInt()) {
            1 -> binding.btnWeather.setImageResource(R.drawable.icon_sunny)
            3 -> binding.btnWeather.setImageResource(R.drawable.icon_sunny_cloudy)
            4 -> binding.btnWeather.setImageResource(R.drawable.icon_cloudy)
        }

    }
}