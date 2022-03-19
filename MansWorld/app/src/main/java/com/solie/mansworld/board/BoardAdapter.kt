package com.solie.mansworld.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solie.mansworld.R
import com.solie.mansworld.databinding.BoardItemShapeBinding

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private var data = mutableListOf<BoardItem>()
    private lateinit var boardClickListener : BoardClickListener

    interface BoardClickListener {
        fun onClick (view : View, position : Int)
    }

    fun boardClickListener (boardClickListener: BoardClickListener) {
        this.boardClickListener = boardClickListener
    }

    class BoardViewHolder(val binding : BoardItemShapeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (boardItem : BoardItem) {
            binding.listshapeTitle.text = boardItem.Title
            binding.listshapeBoard.text = boardItem.BoardName
            binding.listshapeWriter.text = boardItem.NickName
            binding.listshapeDate.text = boardItem.Date
            binding.listshapeRecommend.text = boardItem.Recommend.toString()
            binding.listshapeReplies.text = boardItem.Replies.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<BoardItemShapeBinding>(layoutInflater, R.layout.board_item_shape, parent, false)

        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            boardClickListener.onClick(it, position)
        }
        holder.binding.listshapeTitle.text = data[position].Title
        holder.binding.listshapeWriter.text = data[position].NickName
        holder.binding.listshapeBoard.text = data[position].BoardName
        holder.binding.listshapeDate.text = data[position].Date
        holder.binding.listshapeReplies.text = data[position].Replies.toString()
        holder.binding.listshapeRecommend.text = data[position].Recommend.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun replaceBoard(newList : MutableList<BoardItem>) {
        data = newList.toMutableList()
        notifyDataSetChanged()
    }

}