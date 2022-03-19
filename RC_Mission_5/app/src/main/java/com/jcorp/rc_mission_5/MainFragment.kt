package com.jcorp.rc_mission_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jcorp.rc_common_5.data.FirstItem
import com.jcorp.rc_mission_5.adapter.FirstAdapter
import com.jcorp.rc_mission_5.adapter.SecondAdapter
import com.jcorp.rc_mission_5.data.SecondItem
import com.jcorp.rc_mission_5.databinding.FragmentMainBinding
import com.jcorp.rc_mission_5.util.Firebase
import com.jcorp.rc_mission_5.util.mViewModel

class MainFragment : Fragment(), Firebase {
    private lateinit var binding : FragmentMainBinding
    private val viewModel by viewModels<mViewModel> ({ requireActivity() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setRV()

        return binding.root
    }

    private fun setRV() {
        val firstAdapter = FirstAdapter(requireActivity())
        val firstItemList = mutableListOf<FirstItem>()
        binding.MainRV.adapter = firstAdapter
        firstDataStore.get()
            .addOnSuccessListener { task ->
                for(document in task) {
                    firstItemList.add(document.toObject(FirstItem::class.java))
                    firstAdapter.setItem(firstItemList)
                }
            }


        val secondAdapter = SecondAdapter(requireActivity())
        val secondItemList = mutableListOf<SecondItem>()
        binding.secondRV.adapter = secondAdapter
        secondDataStore.get()
            .addOnSuccessListener { task ->
                for(document in task) {
                    secondItemList.add(document.toObject(SecondItem::class.java))
                    secondAdapter.setItem(secondItemList)
                }
            }

        val thirdAdapter = SecondAdapter(requireActivity())
        val thirdItemList = mutableListOf<SecondItem>()
        binding.thirdRV.adapter = thirdAdapter
        thirdDataStore.get()
            .addOnSuccessListener { task ->
                for(document in task) {
                    thirdItemList.add(document.toObject(SecondItem::class.java))
                    thirdAdapter.setItem(thirdItemList)
                }
            }
    }
}