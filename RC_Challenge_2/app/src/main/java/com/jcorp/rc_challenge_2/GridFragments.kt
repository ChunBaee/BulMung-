package com.jcorp.rc_challenge_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jcorp.rc_challenge_2.databinding.FragmentGridBinding

class GridFragments(position : Int) : Fragment() {
    private lateinit var binding : FragmentGridBinding
    private val mPosition = position
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGridBinding.inflate(inflater, container, false)

        setGrid(mPosition)

        return binding.root
    }

    private fun setGrid(position : Int) {
        when(position) {
            0 -> firstFrag()
            1 -> secondFrag()
            2 -> thirdFrag()
        }
    }

    private fun firstFrag() {
        binding.cardPager1.visibility = View.VISIBLE
        binding.cardPager2.visibility = View.GONE
        binding.cardPager3.visibility = View.GONE
    }

    private fun secondFrag() {
        binding.cardPager1.visibility = View.GONE
        binding.cardPager2.visibility = View.VISIBLE
        binding.cardPager3.visibility = View.GONE
    }

    private fun thirdFrag() {
        binding.cardPager1.visibility = View.GONE
        binding.cardPager2.visibility = View.GONE
        binding.cardPager3.visibility = View.VISIBLE
    }

}