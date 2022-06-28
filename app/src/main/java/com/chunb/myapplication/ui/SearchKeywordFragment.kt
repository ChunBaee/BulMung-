package com.chunb.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.chunb.myapplication.R
import com.chunb.myapplication.ViewModel
import com.chunb.myapplication.adapter.KeywordCampingListAdapter
import com.chunb.myapplication.databinding.FragmentKeywordSearchBinding

class SearchKeywordFragment : Fragment() {
    private lateinit var binding : FragmentKeywordSearchBinding
    private val viewModel by activityViewModels<ViewModel>()

    private val keywordAdapter by lazy {
        KeywordCampingListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentKeywordSearchBinding.inflate(inflater, container, false)

        viewModel.showFab.value = false

        binding.homeEdtSearchByName.setOnKeyListener { view, i, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER && binding.homeEdtSearchByName.text.isNotEmpty()) {
                viewModel.setCurSearchKeyword(binding.homeEdtSearchByName.text.toString())
                viewModel.isKeyword.value = true
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        observe()
        setUi()

        return binding.root
    }

    private fun observe() {
        viewModel.curSearchKeyword.observe(viewLifecycleOwner, Observer {
            viewModel.getKeywordLocationData(it)
        })

        viewModel.keywordBasedCampingData.observe(viewLifecycleOwner, Observer {
            keywordAdapter.getKeywordList(it)
        })
    }

    private fun setUi() {
        binding.keywordItemRv.hasFixedSize()
        binding.keywordItemRv.adapter = keywordAdapter

        keywordAdapter.keywordListClickedListener(object : KeywordCampingListAdapter.KeywordListClickedListener {
            override fun keywordListClickedListener(view: View, position: Int) {
                viewModel.setCurCampingDataFromKeyword(position)
                Log.d("----", "listClickedListener: $position")
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_main_keyword_container, CampingDetailFragment()).addToBackStack("Detail").commit()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteKeywordLocationData()
        viewModel.showFab.value = true
        viewModel.isOnKeyword.value = false
    }
}