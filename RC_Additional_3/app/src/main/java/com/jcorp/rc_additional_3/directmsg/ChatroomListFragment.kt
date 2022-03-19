package com.jcorp.rc_additional_3.directmsg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jcorp.rc_additional_3.databinding.ItemChatroomMessageBinding

class ChatroomListFragment : Fragment() {

    private lateinit var binding : ItemChatroomMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ItemChatroomMessageBinding.inflate(inflater, container, false)



        return binding.root
    }
}