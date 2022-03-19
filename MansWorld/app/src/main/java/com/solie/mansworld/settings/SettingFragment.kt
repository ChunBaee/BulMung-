package com.solie.mansworld.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solie.mansworld.R
import com.solie.mansworld.databinding.FragmentSettingBinding
import com.solie.mansworld.settings.profile.ProfileActivity

class SettingFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.settingProfileMenu.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id) {

            R.id.setting_profile_menu -> {
                val intent = Intent(activity, ProfileActivity::class.java)
                startActivity(intent)
            }

        }
    }

}