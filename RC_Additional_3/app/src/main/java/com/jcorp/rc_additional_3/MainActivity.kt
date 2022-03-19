package com.jcorp.rc_additional_3

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.navigation.NavigationBarView
import com.jcorp.rc_additional_3.databinding.ActivityMainBinding
import com.jcorp.rc_additional_3.directmsg.DMListView
import com.jcorp.rc_additional_3.retrofit.RetrofitManager

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    val viewModel by viewModels<mViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getLoginToken()
        getUserData()

    }

    private fun getLoginToken() {
        if(intent.getStringExtra("Type") == "Naver") {
            Log.d("----", "getLoginToken: 네이버 로그인")
            viewModel.isNaver.value = true
            viewModel.accessToken.value = intent.getStringExtra("Token")

        } else if(intent.getStringExtra("Type") == "Kakao") {
            Log.d("----", "getLoginToken: 카카오 로그인")
            viewModel.isNaver.value = false
            viewModel.accessToken.value = intent.getStringExtra("Token")
        }

        Log.d("----", "getLoginToken: ${viewModel.accessToken.value.toString()}")
    }

    private fun getUserData() {
        //LoginActivity().mNaverLoginModule.requestApi(applicationContext, viewModel.accessToken.value, "https://openapi.naver.com/v1/nid/me")
        RetrofitManager.instance.getNaverData("Bearer ${viewModel.accessToken.value.toString()}") {
            Log.d("----", "getUserData: ${it?.response?.profile_image}")
            viewModel.setUserData(it)
            setUi()
        }
    }

    private fun setUi() {
        val menuItem = binding.bottomView.menu.findItem(R.id.nul)
        binding.bottomView.itemIconTintList = null
        Glide.with(this)
            .asBitmap()
            .load(viewModel.currentUser.value!!.response.profile_image)
            .apply(
                RequestOptions
                .circleCropTransform()
                .placeholder(R.drawable.placeholder))
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    menuItem.icon = BitmapDrawable(resources, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })

        binding.bottomView.setOnItemSelectedListener(this)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_friendList -> {
                //친구창
            }

            R.id.menu_directMsg -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, DMListView()).commit()
            }

            R.id.menu_search -> {
                //탐색창
            }

            R.id.menu_mention -> {
                //멘션창
            }

            R.id.nul -> {
                //프로필창
            }
        }
        return true
    }


}