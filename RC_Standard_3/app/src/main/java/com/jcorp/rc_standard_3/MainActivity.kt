package com.jcorp.rc_standard_3

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jcorp.rc_standard_3.databinding.ActivityMainBinding
import com.leinardi.android.speeddial.SpeedDialActionItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var keepDialog : Dialog

    private val TAG = "MAIN"

    private var isSaved = false
    private var savedTitle = ""
    private var savedWon = ""
    private var savedContent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        keepDialog = Dialog(this)
        keepDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        keepDialog.setContentView(R.layout.keep_write_dialog)

        binding.speedDial.setOnActionSelectedListener {
            binding.overlay.bringToFront()
            when(it.id) {
                R.id.fab_long_label1 -> {
                    when(isSaved) {
                        true -> {
                            showKeepDialog()
                        }
                        false -> {
                            val intent = Intent(applicationContext, WriteActivity::class.java)
                            startActivityForResult(intent, 1)
                        }
                    }
                    Toast.makeText(applicationContext, "1번", Toast.LENGTH_SHORT).show()
                    binding.speedDial.close()
                    return@setOnActionSelectedListener true
                }

                R.id.fab_long_label2 -> {
                    Toast.makeText(applicationContext, "2번", Toast.LENGTH_SHORT).show()
                    binding.speedDial.close()
                    return@setOnActionSelectedListener true
                }
            }
            false
        }

        setToolbar()
        setList()
        setFab()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolBar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayShowCustomEnabled(true)
        actionbar.setDisplayShowTitleEnabled(false)
        actionbar.setDisplayHomeAsUpEnabled(false)
    }

    private fun setList() {
        val adapter = mAdapter()
        val list = mutableListOf<mItem>()
        binding.recyclerList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        binding.recyclerList.adapter = adapter
        binding.recyclerList.setHasFixedSize(true)

        list.add(mItem(R.drawable.img_dangun, "AG님, 당근에 나타났던 위드유를 기억하시나요?", "당근마켓", null, null, false, null, false, null))
        list.add(mItem(R.drawable.img_louisvuitton, "루이비통 종이가방", "복현동", "15초 전", "5,000원", false, null, false, null))
        list.add(mItem(R.drawable.img_thombrowne, "톰브라운 카드지갑 팔아요", "산격2동", "끌올 8분 전", "170,000원", true, 10, true, 15))
        list.add(mItem(R.drawable.img_applewatch, "애플워치se 스페이스그레이 스그 44mm gps 팝니다", "산격동", "끌올 29초 전", "300,000원", true, 11, true, 16))
        list.add(mItem(R.drawable.img_airpods, "에어팟 3 미개봉", "복현동", "2분 전", "198,000원", false, null, false, null))
        list.add(mItem(R.drawable.img_jejugrand, "제주 그랜드 하얏트 호텔 숙박권", "중구 태평로1가", "끌올 36초 전", "330,000원", true, 1, true, 9))
        adapter.setContent(list)
    }

    private fun setFab() {

        binding.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.fab_long_label1, R.drawable.icon_pencil)
            .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white, theme))
            .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.dangun_sig, theme))
            .setLabel("중고거래")
            .setLabelColor(Color.WHITE)
            .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.noColor, theme))
            .setLabelClickable(false)
            .create())


        binding.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.fab_long_label2, R.drawable.icon_hongbo)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.dangun_sig, theme))
                .setLabel("동네홍보")
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.noColor, theme))
                .setLabelClickable(false)
                .create())
    }

    private fun showKeepDialog() {
        keepDialog.show()
        keepDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val x = (size.x * 0.85f).toInt()
        val y = (size.y * 0.23f).toInt()
        keepDialog.window!!.setLayout(x, y)


        keepDialog.findViewById<Button>(R.id.dialog_yes).setOnClickListener {
            val intent = Intent(applicationContext, WriteActivity::class.java)
            intent.putExtra("title", savedTitle)
            intent.putExtra("price", savedWon)
            intent.putExtra("content", savedContent)
            startActivityForResult(intent, 1)
            keepDialog.dismiss()
        }
        keepDialog.findViewById<Button>(R.id.dialog_no).setOnClickListener {
            val intent = Intent(applicationContext, WriteActivity::class.java)
            startActivityForResult(intent, 1)
            keepDialog.dismiss()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: onResult 호출")
        when(requestCode) {
            1 -> {
                when (resultCode) {
                    RESULT_OK -> {
                        Log.d(TAG, "onActivityResult: TITLE : ${data!!.getStringExtra("title").toString()}")
                        Log.d(TAG, "onActivityResult: WON : ${data!!.getStringExtra("price").toString()}")
                        Log.d(TAG, "onActivityResult: CONTENT : ${data!!.getStringExtra("content").toString()}")

                        savedTitle = data!!.getStringExtra("title").toString()
                        savedWon = data!!.getStringExtra("price").toString()
                        savedContent = data!!.getStringExtra("content").toString()
                    }
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: onResume() 호출됨")

        isSaved = if(savedTitle.isNotEmpty() || savedWon.isNotEmpty() || savedContent.isNotEmpty()) {
            Snackbar.make(binding.snackbarContainer, "게시글이 임시저장되었어요", Snackbar.LENGTH_SHORT).show()
            true
        } else {
            false
        }

    }

}