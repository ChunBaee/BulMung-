package com.jcorp.rc_standard_4.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcorp.rc_standard_4.RvAdapter
import com.jcorp.rc_standard_4.ViewModel
import com.jcorp.rc_standard_4.data.MemoListItem
import com.jcorp.rc_standard_4.databinding.FragmentMemolistBinding

class MemoListFragment : Fragment() {

    private lateinit var binding : FragmentMemolistBinding
    private val viewModel : ViewModel by viewModels({requireActivity()})

    private lateinit var adapter : RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMemolistBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = RvAdapter()

        viewModel.memoList.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        setRv()

        return binding.root
    }

    private fun setRv() {
        binding.rvList.adapter = adapter
        binding.rvList.setHasFixedSize(true)
        binding.rvList.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        binding.rvLayout.clipToOutline = true

        adapter.memoClickListener(object : RvAdapter.MemoClickListener {
            override fun onClick(view: View, position: Int) {
                viewModel.editPosition(position)
                viewModel.isEditState(true)
            }
        })

        adapter.memoLongClickListener(object : RvAdapter.MemoLongClickListener{
            override fun onLongClick(view: View, position: Int) {
                Toast.makeText(requireActivity().applicationContext, "롱클릭 $position", Toast.LENGTH_SHORT).show()
                viewModel.deleteMemo(position)
            }

        })
    }
}