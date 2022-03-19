package com.solie.mansworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.solie.mansworld.board.BoardFragment
import com.solie.mansworld.data.FirebaseData
import com.solie.mansworld.databinding.ActivityMainBinding
import com.solie.mansworld.login.LoginActivity
import com.solie.mansworld.settings.SettingFragment
import com.solie.mansworld.settings.profile.ProfileActivity
import com.solie.mansworld.sidemenu.SideMenuFragment
import com.solie.mansworld.sidemenu.SideMenuItem
import com.solie.mansworld.util.NetworkStatus
import com.solie.mansworld.viewmodel.FirebaseViewModel
import com.solie.mansworld.sidemenu.SideMenuAdapter as SideMenuAdapter1

class MainActivity : AppCompatActivity(), FirebaseData {

    private lateinit var binding : ActivityMainBinding
    private val firebaseModel : FirebaseViewModel by viewModels()

    private var sideMenuFragment = SideMenuFragment()
    private var boardList = arrayOf<String>()

    private val GET_USER_INFO = 100
    private val OLD_USER = 0
    private val NEW_USER = 1

    private var boardFragment = BoardFragment()
    private var settingFragment = SettingFragment()

    private var translateLeft: Animation? = null
    private var translateRight: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        translateLeft = AnimationUtils.loadAnimation(applicationContext, R.anim.translate_left)
        translateRight = AnimationUtils.loadAnimation(applicationContext, R.anim.translate_right)

        checkNetwork()
        setSideMenu()
        sideMenuFragment
        setBotNav()
    }

    private fun checkNetwork() {
        var netWorkStatus = NetworkStatus(applicationContext).checkNetworkStatus()
        if(!netWorkStatus) {
            Toast.makeText(applicationContext, "인터넷 연결을 먼저 확인해주세요.", Toast.LENGTH_SHORT).show()
            Log.d("DEVG", "인터넷연결성공")
            finish()
        } else {
            userAuthCheck()
        }
    }

    private fun userAuthCheck() {
        if(currentUser == null) {
            Log.d("DEVG", "로그인화면이동")
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivityForResult(intent, GET_USER_INFO)
        } else {
            setToolbar()
        }
    }

   private fun setSideMenu() {
        val mData = mutableListOf<SideMenuItem>()
        val adapter : SideMenuAdapter1 = SideMenuAdapter1()

        boardList = resources.getStringArray(R.array.board_list)

        mData.apply {
            for(element in boardList) {
                add(SideMenuItem(element))
            }
        }
        adapter.replaceList(mData)
        binding.sideMenuRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.sideMenuRecycler.adapter = adapter

       adapter.setItemClickListener(object : com.solie.mansworld.sidemenu.SideMenuAdapter.ItemClickListener {
           override fun onClick(view: View, position: Int) {
               //사이드메뉴 리사이클러 클릭이벤
               Toast.makeText(applicationContext, "${position}번 리스트 선택", Toast.LENGTH_SHORT).show()
           }
       })

   }

    private fun setToolbar() {
        setSupportActionBar(binding.MainToolbar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayShowCustomEnabled(true)
        actionbar!!.setDisplayShowTitleEnabled(false)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        changeFrag(boardFragment)
    }

    private fun setBotNav() {
        binding.MainBotNav.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.listPage -> {
                        binding.MainToolbar.title = "게시글 목록"
                        Toast.makeText(applicationContext, "게시판눌림",Toast.LENGTH_SHORT).show()
                        changeFrag(boardFragment)
                    }

                    R.id.userPage -> {
                        binding.MainToolbar.title = "설정 정보"
                        Toast.makeText(applicationContext, "설정눌림",Toast.LENGTH_SHORT).show()
                        changeFrag(settingFragment)
                    }
                }
                true
            }
        }
    }

    private fun changeFrag (fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.MainContainer, fragment).commit()
    }

     /*private fun sideMenuAnimate() {
        binding.MainSideMenu.visibility = View.VISIBLE
        binding.MainSideMenu.isClickable = true
        binding.MainSideMenu.animation = translateRight


    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_USER_INFO) {
            val newUser = data!!.getIntExtra("UserCheck", NEW_USER)
            if(newUser == NEW_USER) {
                Log.d("DEVG", "신규유저")
                val intent = Intent(applicationContext, ProfileActivity::class.java)
                startActivityForResult(intent, 0)
            } else {
                Log.d("DEVG", "기존유저")
            }
        } else if(requestCode == 0) {
            binding.MainToolbar.title = "게시글 목록"
            changeFrag(boardFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
             //   sideMenuAnimate()
                binding.MainDrawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
