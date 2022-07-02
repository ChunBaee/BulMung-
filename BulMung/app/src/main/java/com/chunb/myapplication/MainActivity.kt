package com.chunb.myapplication

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.chunb.myapplication.data.KakaoLocationSearchData
import com.chunb.myapplication.databinding.ActivityMainBinding
import com.chunb.myapplication.ui.*
import com.chunb.myapplication.util.ThemeManager
import com.kakao.sdk.common.KakaoSdk
import com.skt.Tmap.TMapTapi


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<ViewModel>()
    private var isMap = true

    val pref: SharedPreferences by lazy {
        getSharedPreferences("UserSettings", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Log.d("----", "onCreate: STARTED")

        setUi()
        setTmap()
        observe()

        binding.mapChangeFab.setOnClickListener(this)
        binding.homeMainBtnMenu.setOnClickListener(this)
        binding.homeBtnFindLocation.setOnClickListener(this)
        binding.homeBtnSetting.setOnClickListener(this)
        binding.homeMainLogo.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        getPref()
    }

    private fun getPref() {
        viewModel.mapSeekRange.value = pref.getInt("SeekRange", 20000)
        viewModel.mThemeIsDark.value = pref.getBoolean("IsDark", false)
        viewModel.mNaviSelected.value = pref.getInt("SelectedNavi", 0)
        setMap()
    }

    private fun setMap() {
        supportFragmentManager.beginTransaction().replace(R.id.home_main_container, MapFragment()).commit()
        viewModel.useUserLocation()
    }

    private fun setUi() {
        binding.homeMainBtnSearch.setOnClickListener {
            Log.d("----", "setUi: CLICK")
            viewModel.isOnKeyword.value = true
            supportFragmentManager.beginTransaction().replace(R.id.home_main_keyword_container, SearchKeywordFragment()).addToBackStack("KEYWORD").commit()
        }
    }

    private fun setTmap() {
        viewModel.tMap = TMapTapi(this)
        viewModel.tMap!!.setSKTMapAuthentication("l7xx223e92c3f6764f5eb7a1072125521af4")
        Log.d("----", "setTmap: ${viewModel.tMap!!.isTmapApplicationInstalled}")

        KakaoSdk.init(applicationContext, "eb50d5691f783c04f0082c52bf7a40e8")
    }

    private fun observe() {
        viewModel.showFab.observe(this, Observer {
            when(it) {
                false -> binding.mapChangeFab.visibility = View.GONE
                else -> binding.mapChangeFab.visibility = View.VISIBLE
            }
        })

        viewModel.isOnKeyword.observe(this, Observer {
            when(it) {
                true -> binding.homeMainBtnSearch.isClickable = false
                else -> binding.homeMainBtnSearch.isClickable = true
            }
        })

        viewModel.mapSeekRange.observe(this, Observer {
            Log.d("9999", "observe: $it")
            pref.edit().putInt("SeekRange", it).commit()
        })

        viewModel.mThemeIsDark.observe(this, Observer {
            pref.edit().putBoolean("IsDark", it).commit()
        })

        viewModel.mNaviSelected.observe(this, Observer {
            pref.edit().putInt("SelectedNavi", it).commit()
        })
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            //지도 맵 바꾸는 플로팅버튼
            R.id.map_change_fab -> {
                isMap = if(isMap) {
                    binding.mapChangeFabImage.setImageResource(R.drawable.icon_fab_change_to_map)
                    supportFragmentManager.beginTransaction().replace(R.id.home_main_container, ListFragment()).commit()
                    !isMap
                } else {
                    binding.mapChangeFabImage.setImageResource(R.drawable.icon_fab_change_to_list)
                    supportFragmentManager.beginTransaction().replace(R.id.home_main_container, MapFragment()).commit()
                    !isMap
                }
            }
            //검색창 및 지역선택 클릭하는 뷰
            R.id.home_main_btn_menu -> {
                when(binding.dialogs.visibility) {
                    View.VISIBLE -> {
                        binding.dialogs.visibility = View.GONE
                    }
                    else -> {
                        binding.dialogs.visibility = View.VISIBLE
                        binding.dialogs.translationZ = 1F
                    }
                }
            }
            //위치 검색창
            R.id.home_btn_find_location -> {
                binding.dialogs.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.home_main_container, SearchLocationFragment()).addToBackStack(null).commit()
            }

            R.id.home_btn_setting -> {
                binding.dialogs.visibility = View.GONE
                SettingDialog().show(supportFragmentManager, "Setting")
            }

            R.id.home_main_logo -> {
                viewModel.useUserLocation()
                viewModel.isKeyword.value = false
            }
        }
    }
}