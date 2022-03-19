package com.jcorp.rc_mission_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jcorp.rc_mission_5.data.PlaylistItem
import com.jcorp.rc_mission_5.databinding.ActivityMainBinding
import com.jcorp.rc_mission_5.util.Firebase
import com.jcorp.rc_mission_5.util.mViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener, Firebase {

    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<mViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.btnPlayList.setOnClickListener(this)
        binding.btnPlay.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)
        binding.txtPlayTitle.setOnClickListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MainFragment()).commit()

        observe()
        getInfo()

    }

    private fun observe() {
        viewModel.toPlayFrag.observe(this, Observer {
            when(it) {
                true -> {
                    binding.playContainer.isClickable = true
                    binding.playContainer.bringToFront()

                    binding.bottomView.visibility = View.GONE

                    binding.mainContainer.isClickable = false
                }
                false -> {
                    binding.mainContainer.isClickable = true
                    binding.mainContainer.bringToFront()

                    binding.bottomView.visibility = View.VISIBLE

                    binding.playContainer.isClickable = false
                }
            }
        })

        viewModel.isPlaying.observe(this, Observer {
            when(it) {
                true -> {
                    binding.btnPlay.setImageResource(R.drawable.icon_pause)
                }

                false -> {
                    binding.btnPlay.setImageResource(R.drawable.icon_play)
                }
            }
        })

        viewModel.nowPlaying.observe(this, Observer {
            binding.txtPlayTitle.text = it.title
            binding.txtPlaySinger.text = it.singer
        })

    }

    private fun getInfo() {
        playListStore.get().addOnSuccessListener {
            for (document in it) {

                viewModel.mPlayList.add(document.toObject(PlaylistItem::class.java))

                viewModel.setPlayList()
                viewModel.mHeartList.add(false)
            }
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_playList -> {
                viewModel.isToPlayList(true)
                viewModel.toPlayFrag(true)
                supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.playContainer, PlayFragment()).commit()
            }
            R.id.btn_play -> {
                when(viewModel.isPlaying.value) {
                    true -> {
                        viewModel.setIsPlaying(false)
                        viewModel.mPlayList[viewModel.playPosition.value!!].isPlaying = false
                        viewModel.setPlayList()
                    }

                    false, null -> {
                        viewModel.setIsPlaying(true)
                        viewModel.mPlayList[viewModel.playPosition.value!!].isPlaying = true
                        viewModel.setPlayList()
                    }
                }
            }
            R.id.btn_next -> {
                //플레이상태 업데이트 및 다음곡
            }
            R.id.btn_previous -> {
                // 플레이상태 업데이트 및 이전곡
            }
            R.id.txt_play_title -> {
                viewModel.isToPlayList(false)
                viewModel.toPlayFrag(true)
                supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.playContainer, PlayFragment()).commit()
            }
        }
    }
}