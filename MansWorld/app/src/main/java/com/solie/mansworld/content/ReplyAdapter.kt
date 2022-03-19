package com.solie.mansworld.content

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.solie.mansworld.R
import com.solie.mansworld.board.BoardItem
import com.solie.mansworld.databinding.ReplyContentShapeBinding

class ReplyAdapter (val context : Context): BaseAdapter() {

    private var replyItems = mutableListOf<ReplyItem>()

    override fun getCount(): Int {
        return replyItems.size
    }

    override fun getItem(position: Int): Any {
        return replyItems[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ReplyContentShapeBinding>(layoutInflater, R.layout.reply_content_shape, parent, false)

        val item = replyItems[position]

        binding.replyShapeNick.text = item.UserNick
        binding.replyShapeTime.text = item.ReplyDate
        binding.replyShapeContent.text = item.Content

        return binding.root
    }

    fun replaceBoard(newList : MutableList<ReplyItem>) {
        replyItems = newList.toMutableList()
        notifyDataSetChanged()
    }
}