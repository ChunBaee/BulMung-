package com.jcorp.rc_standard_4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.jcorp.rc_standard_4.data.DialogListItem
import com.jcorp.rc_standard_4.databinding.ItemDialogListBinding

class ListAdapter(private val context : Context, list : MutableList<DialogListItem>) : BaseAdapter() {
    var list = list
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemDialogListBinding.inflate(LayoutInflater.from(context))
        binding.dialogListTitle.text = list[position].title
        binding.dialogListIcon.setImageResource(list[position].img)

        return binding.root
    }
}