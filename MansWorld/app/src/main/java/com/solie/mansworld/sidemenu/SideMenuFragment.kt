package com.solie.mansworld.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.solie.mansworld.R
import com.solie.mansworld.databinding.SidemenuBoardlistBinding

class SideMenuFragment : Fragment() {

    private lateinit var binding : SidemenuBoardlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SidemenuBoardlistBinding.inflate(inflater, container, false)

        return binding.root
    }

}
