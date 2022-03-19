package com.jcorp.rc_mission_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.jcorp.rc_mission_5.databinding.FragmentPlayBinding
import com.jcorp.rc_mission_5.util.mViewModel

class PlayFragment : Fragment(), View.OnClickListener {
    private lateinit var binding : FragmentPlayBinding
    private val viewModel : mViewModel by viewModels({requireActivity()})
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlayBinding.inflate(inflater, container, false)

        binding.playBottomPlay.setOnClickListener(this)
        binding.playBottomList.setOnClickListener(this)

        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.isToPlayList.observe(viewLifecycleOwner, Observer {
            when(it) {
                true -> {
                    binding.playBottomProgress.visibility = View.VISIBLE
                    requireActivity().supportFragmentManager.beginTransaction().add(R.id.playContainer, ListFragment()).commit()
                }// 플레이리스트 화면

                false -> {
                }// 재생화면
            }
        })

        viewModel.nowPlaying.observe(viewLifecycleOwner, Observer {
            if(viewModel.isToPlayList.value == true && it != null) {
                Glide.with(requireActivity())
                    .load(it.album)
                    .into(binding.playBottomList)

                Glide.with(requireActivity())
                    .load(it.album)
                    .into(binding.playBackgroundImg)
            }
        })

        viewModel.isPlaying.observe(viewLifecycleOwner, Observer {
            when(it) {
                true -> binding.playBottomPlay.setImageResource(R.drawable.icon_pause)

                false -> binding.playBottomPlay.setImageResource(R.drawable.icon_play)
            }
        })

    }

    override fun onDetach() {
        viewModel.toPlayFrag(false)
        super.onDetach()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.play_bottom_play -> {
                when(viewModel.isPlaying.value) {
                    true -> {
                        viewModel.setIsPlaying(false)
                        viewModel.mPlayList[viewModel.playPosition.value!!].isPlaying = false
                        viewModel.setPlayList()
                    }

                    else -> {
                        viewModel.setIsPlaying(true)
                        viewModel.mPlayList[viewModel.playPosition.value!!].isPlaying = true
                        viewModel.setPlayList()
                    }
                }
            }

            R.id.play_bottom_list -> {
                binding.playBottomList.setImageResource(R.drawable.icon_playlist)
                binding.playBottomProgress.visibility = View.GONE
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.playContainer, MusicFragment()).commit()
            }
        }
    }

}