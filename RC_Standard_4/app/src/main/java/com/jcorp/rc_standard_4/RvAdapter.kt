package com.jcorp.rc_standard_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_standard_4.data.MemoListItem
import com.jcorp.rc_standard_4.databinding.ItemMemolistBinding
import java.text.SimpleDateFormat

class RvAdapter : RecyclerView.Adapter<RvAdapter.mViewHolder>() {
    private var data = mutableListOf<MemoListItem>()
    private lateinit var memoClickListener : MemoClickListener
    private lateinit var memoLongClickListener : MemoLongClickListener

    interface MemoClickListener {
        fun onClick (view : View, position : Int)
    }
    interface MemoLongClickListener {
        fun onLongClick (view: View, position : Int)
    }

    fun memoClickListener (memoCLickListener : MemoClickListener) {
        this.memoClickListener = memoCLickListener
    }
    fun memoLongClickListener (memoLongClickListener: MemoLongClickListener) {
        this.memoLongClickListener = memoLongClickListener
    }

    class mViewHolder(private val binding : ItemMemolistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : MemoListItem) {
            binding.memoListTitle.text = item.title

            if(item.date == SimpleDateFormat("yyyy년 MM월 dd일").format(System.currentTimeMillis())) {
                binding.memoListDate.text = item.time
            } else {
                binding.memoListDate.text = item.date + item.time
            }

            if(item.content != null) {
                binding.memoListContent.text = item.content
            } else {
                binding.memoListContent.text = "추가 텍스트 없음"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMemolistBinding>(layoutInflater, R.layout.item_memolist, parent, false)

        return mViewHolder(binding)

    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            memoClickListener.onClick(it, position)
        }
        holder.itemView.setOnLongClickListener {
            memoLongClickListener.onLongClick(it, position)
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList (list : MutableList<MemoListItem>) {
        data = list
        notifyDataSetChanged()
    }

}