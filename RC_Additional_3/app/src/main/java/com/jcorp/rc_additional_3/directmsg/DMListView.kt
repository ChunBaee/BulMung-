package com.jcorp.rc_additional_3.directmsg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jcorp.rc_additional_3.R
import com.jcorp.rc_additional_3.databinding.ViewDmListBinding
import com.jcorp.rc_additional_3.directmsg.adapter.DmListAdapter
import com.jcorp.rc_additional_3.directmsg.data.ItemDmList

class DMListView : Fragment() {
    private lateinit var binding : ViewDmListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ViewDmListBinding.inflate(inflater, container, false)


        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.dmList_container, ChatroomListFragment()).commit()

        val adapter = DmListAdapter(requireActivity().baseContext)
        binding.sideDmList.adapter = adapter
        val list = mutableListOf<ItemDmList>()
        for(i in 0 until 5) {
            list.add(ItemDmList(R.drawable.ic_launcher_background,R.drawable.btn_dm_list_unclicked, R.drawable.dm_list_sidebar_unclicked))
        }
        adapter.setData(list)

        return binding.root
    }
}