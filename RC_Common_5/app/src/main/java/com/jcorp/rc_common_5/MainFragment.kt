package com.jcorp.rc_common_5

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jcorp.rc_common_5.databinding.FragmentMainBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private val viewModel by viewModels<ViewModel>({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_play, PlayFragment()).commit()

        setRv()

        return binding.root
    }

    private fun setRv() {
        binding.mainFirstRv.adapter = FirstAdapter()
    }


}