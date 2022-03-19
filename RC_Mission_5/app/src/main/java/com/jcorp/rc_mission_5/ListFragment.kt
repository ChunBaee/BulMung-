package com.jcorp.rc_mission_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jcorp.rc_mission_5.adapter.PlaylistAdapter
import com.jcorp.rc_mission_5.databinding.FragmentListBinding
import com.jcorp.rc_mission_5.util.Firebase
import com.jcorp.rc_mission_5.util.mViewModel
import devlight.io.library.ntb.NavigationTabBar

class ListFragment : Fragment(), Firebase {
    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<mViewModel>({ requireActivity() })
    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.appBar.outlineProvider = null

        setTab()
        setRv()
        observe()

        return binding.root
    }

    private fun setTab() {
        var models = mutableListOf<NavigationTabBar.Model>()
        models.add(
            NavigationTabBar.Model.Builder(
                resources.getDrawable(R.drawable.icon_music),
                resources.getColor(R.color.no_color)
            ).title("곡")
                .badgeTitle("NTB")
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                resources.getDrawable(R.drawable.icon_musiclist),
                resources.getColor(R.color.no_color)
            ).title("플레이리스트")
                .badgeTitle("NTB")
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                resources.getDrawable(R.drawable.icon_video),
                resources.getColor(R.color.no_color)
            ).title("비디오")
                .badgeTitle("NTB")
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                resources.getDrawable(R.drawable.icon_language),
                resources.getColor(R.color.no_color)
            ).title("어학")
                .badgeTitle("NTB")
                .build()
        )

        binding.tabLayout.models = models
    }

    private fun setRv() {
        adapter = PlaylistAdapter(requireActivity().applicationContext)
        binding.playlistRv.adapter = adapter

        adapter.clickListener(object : PlaylistAdapter.ClickListener {
            override fun onClicked(view: View, position: Int) {

                viewModel.playPosition.value = position

                if (position == viewModel.prePosition) {
                    if (viewModel.isPlaying.value == false) {
                        viewModel.setIsPlaying(true)
                    }
                } else {
                    if(viewModel.prePosition != null) {
                        viewModel.mPlayList[viewModel.prePosition!!].isPlaying = false
                    }

                    viewModel.mPlayList[position].isPlaying = true
                    viewModel.setNowPlaying(viewModel.PlayList!!.value!![position])
                    viewModel.setIsPlaying(true)

                    viewModel.setPlayList()

                    viewModel.prePosition = position
                }
            }
        })
    }

    private fun observe() {
        viewModel.PlayList.observe(viewLifecycleOwner, Observer {
            adapter.setItem(it)
        })
    }
}