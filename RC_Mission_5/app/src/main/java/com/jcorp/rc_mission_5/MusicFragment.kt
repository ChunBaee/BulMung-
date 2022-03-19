package com.jcorp.rc_mission_5

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
import com.bumptech.glide.Glide
import com.jcorp.rc_mission_5.databinding.FragmentMusicBinding
import com.jcorp.rc_mission_5.util.mViewModel
import java.text.DecimalFormat
import java.util.*

class MusicFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMusicBinding
    private val viewModel: mViewModel by viewModels({ requireActivity() })
    private val mFormatter = DecimalFormat("###,###")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMusicBinding.inflate(inflater, container, false)

        binding.musicHeart.setOnClickListener(this)

        Glide.with(requireActivity()).load(viewModel.nowPlaying.value!!.album)
            .into(binding.background)



        set()
        observe()

        binding.musicHeartAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.musicHeartAnim.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })

        return binding.root
    }

    private fun observe() {
        viewModel.isHeartPressed.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it[viewModel.playPosition.value!!]) {
                true -> {
                    binding.musicHeart.setImageResource(R.drawable.icon_heart_filled)

                    viewModel.nowPlaying.value!!.likeCount =
                        viewModel.nowPlaying.value!!.likeCount?.plus(1)
                    binding.musicHeartCount.text =
                        mFormatter.format(viewModel.nowPlaying.value!!.likeCount)

                    //binding.musicHeartCount.text = (binding.musicHeartCount.text.toString().toInt() + 1).toString()
                }
                false -> {
                    binding.musicHeart.setImageResource(R.drawable.icon_empty_heart)
                    viewModel.nowPlaying.value!!.likeCount =
                        viewModel.nowPlaying.value!!.likeCount?.minus(1)
                    binding.musicHeartCount.text =
                        mFormatter.format(viewModel.nowPlaying.value!!.likeCount)
                    //binding.musicHeartCount.text = (binding.musicHeartCount.text.toString().toInt() - 1).toString()
                }
            }
        })
    }

    private fun set() {
        binding.musicTitle.text = viewModel.nowPlaying.value!!.title
        binding.musicSinger.text = viewModel.nowPlaying.value!!.singer
        binding.musicLyric.text = viewModel.nowPlaying.value!!.lyric
        binding.musicFinishMin.text = viewModel.nowPlaying.value!!.runningTime

        Glide.with(requireActivity())
            .load(viewModel.nowPlaying.value!!.album)
            .into(binding.musicAlbum)



        binding.musicHeartCount.text = mFormatter.format(viewModel.nowPlaying.value!!.likeCount)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.music_heart -> {
                viewModel.heartPressed()
                if (viewModel.isHeartPressed.value!![viewModel.playPosition.value!!]) {
                    binding.musicHeartAnim.visibility = View.VISIBLE
                    binding.musicHeartAnim.repeatCount = 1
                    binding.musicHeartAnim.playAnimation()
                }
            }
        }
    }
}