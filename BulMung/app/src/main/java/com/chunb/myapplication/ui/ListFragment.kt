package com.chunb.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.chunb.myapplication.R
import com.chunb.myapplication.ViewModel
import com.chunb.myapplication.adapter.CampingListAdapter
import com.chunb.myapplication.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel by activityViewModels<ViewModel>()

    private val adapter by lazy {
        CampingListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        setList()
        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.locationBasedCampingData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setCampingList(it)
            }
        })
    }

    private fun setList() {
        binding.listCampingRv.hasFixedSize()
        binding.listCampingRv.adapter = adapter

        adapter.searchClickedListener(object : CampingListAdapter.ListClickedListener {
            override fun listClickedListener(view: View, position: Int) {
                viewModel.setCurCampingData(position)
                Log.d("----", "listClickedListener: $position")
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_main_keyword_container, CampingDetailFragment()).addToBackStack("Detail").commit()
            }
        })
    }
}