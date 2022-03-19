package com.jcorp.rc_standard_4_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_standard_4_2.databinding.RvItemBinding

class RvAdapter : RecyclerView.Adapter<RvAdapter.mViewHolder>() {

    private var mTitles = mutableListOf<String>()
    private var mBools = mutableListOf<Boolean>()

    private lateinit var chekcedListener : CheckedListener

    interface CheckedListener {
        fun onChecked (view : View, position : Int)
    }

    fun checkedListener (checkedListener : CheckedListener) {
        this.chekcedListener = checkedListener
    }

    class mViewHolder (private val binding : RvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item : RvItem) {
            binding.rvTitle.text = item.title
            binding.rvCheck.isChecked = item.checked
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RvItemBinding>(layoutInflater, R.layout.rv_item, parent, false)

        return mViewHolder(binding)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(RvItem(mTitles[position], mBools[position]))
        holder.itemView.setOnClickListener {
            chekcedListener.onChecked(it, position)
        }
    }

    override fun getItemCount(): Int {
        return mTitles.size
    }

    fun setList (list : MutableList<String>, bool : MutableList<Boolean>) {
        mTitles = list
        mBools = bool
        notifyDataSetChanged()
    }

    fun editBool (bool : MutableList<Boolean>) {
        mBools = bool
        notifyDataSetChanged()
    }
}