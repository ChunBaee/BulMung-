package com.jcorp.rc_common_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jcorp.rc_common_5.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {
    private lateinit var binding : FragmentPlayBinding
    private val viewModel by viewModels<ViewModel>({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlayBinding.inflate(inflater, container, false)

        observe()

        return binding.root
    }

    private fun observe() {

        viewModel.slidingOffset.observe(viewLifecycleOwner, Observer {
            if(it == 1.0F) {
                binding.playMinLayout.visibility = View.GONE
            } else {
                binding.playMinLayout.visibility = View.VISIBLE
            }
            binding.playMinLayout.alpha = 1 - it
        })

    }
}