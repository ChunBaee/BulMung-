package com.jcorp.rc_additional_3.directmsg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jcorp.rc_additional_3.databinding.FragmentDirectMessageBinding

class DMFragment : Fragment() {
    private lateinit var binding : FragmentDirectMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDirectMessageBinding.inflate(inflater, container, false)

        setPager()

        return binding.root
    }

    private fun setPager() {

    }
}