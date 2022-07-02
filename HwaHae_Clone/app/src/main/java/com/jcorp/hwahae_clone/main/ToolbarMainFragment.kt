package com.jcorp.hwahae_clone.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jcorp.hwahae_clone.databinding.ToolbarMainBinding

class ToolbarMainFragment : Fragment() {
    private lateinit var binding : ToolbarMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ToolbarMainBinding.inflate(inflater, container, false)
        return binding.root
    }
}