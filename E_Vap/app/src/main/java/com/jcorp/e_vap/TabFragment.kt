package com.jcorp.e_vap

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.MapView
import com.google.maps.android.clustering.ClusterManager
import com.jcorp.e_vap.databinding.FragmentTabBinding
import com.jcorp.e_vap.model.TabItem
import com.jcorp.e_vap.viewmodel.MapViewModel
import com.jcorp.e_vap.viewmodel.MapViewModelFactory
import kotlin.math.absoluteValue

class TabFragment : Fragment() {

    private lateinit var binding: FragmentTabBinding
    private val adapter = TabAdapter()
    private var mPosition: Int? = 0
    private val liveData = MutableLiveData<Int>()

    private var intent = Intent()
    //private val activity = MainActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTabBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        intent = Intent(requireActivity().applicationContext, MainActivity::class.java)

        liveData.observe(viewLifecycleOwner, Observer {
            mPosition = it
            setRecycler(mPosition!!)
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        liveData.value = arguments?.getInt("position")
        Log.d(TAG, "onAttach: ${liveData.value} // onRESUME")
    }

    override fun onPause() {
        super.onPause()
        liveData.value = arguments?.getInt("position")
        Log.d(TAG, "onAttach: ${liveData.value} // onPAUSE")
    }

    private fun setRecycler(position: Int) {
        binding.tabRecycler.adapter = adapter
        binding.tabRecycler.setHasFixedSize(true)
        Log.d(TAG, "onAttach: ${position} // POSITION")

        when (position) {
            0 -> {
                adapter.data = listOf(
                    TabItem(R.drawable.icon_list, "전체"),
                    TabItem(R.drawable.ac_type_1, "AC 완속"),
                    TabItem(R.drawable.chademo, "DC 차데모"),
                    TabItem(R.drawable.dc_combo, "DC 콤보"),
                    TabItem(R.drawable.ac_3, "AC 상")
                )
            }

            1 -> {
                adapter.data = listOf(
                    TabItem(R.drawable.icon_list, "전체"),
                    TabItem(R.drawable.icon_can_charge, "충전 대기"),
                    TabItem(R.drawable.icon_in_charge, "충전중"),
                    TabItem(R.drawable.icon_inspection, "점검중"),
                    TabItem(R.drawable.icon_error, "통신 이상"),
                )
            }
        }

        adapter.tabClickListener(object : TabAdapter.TabClickListener {
            override fun onClick(view: View, position: Int) {
                Log.d(TAG, "onClick: TypeFilter : $mPosition, $position ")
                when (mPosition) {
                    /*0 -> {
                        when (position) {
                            0 -> {
                                viewModelFactory = MapViewModelFactory(mPosition!!, position)
                                viewModel.getMapLiveData().observe(requireActivity(), Observer {
                                })
                            }

                            1 -> {
                                viewModelFactory = MapViewModelFactory(mPosition!!, position)
                                viewModel.getMapLiveData().observe(requireActivity(), Observer {
                                })
                            }

                            2 -> {
                                viewModelFactory = MapViewModelFactory(mPosition!!, position)
                                viewModel.getMapLiveData().observe(requireActivity(), Observer {

                                })
                            }

                            3 -> {
                                viewModelFactory = MapViewModelFactory(mPosition!!, position)
                                viewModel.getMapLiveData().observe(requireActivity(), Observer {

                                })
                            }

                            4 -> {
                                viewModelFactory = MapViewModelFactory(mPosition!!, position)
                                viewModel.getMapLiveData().observe(requireActivity(), Observer {

                                })
                            }
                        }
                    }

                    1 -> {
                        viewModelFactory = MapViewModelFactory(mPosition!!, position)
                    }*/
                }
            }

        })
    }
}