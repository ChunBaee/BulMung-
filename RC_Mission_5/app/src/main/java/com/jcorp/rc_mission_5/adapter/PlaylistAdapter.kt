package com.jcorp.rc_mission_5.adapter

import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.jcorp.rc_mission_5.R
import com.jcorp.rc_mission_5.data.PlaylistItem
import com.jcorp.rc_mission_5.databinding.ItemPlaylistBinding

class PlaylistAdapter (context : Context) : RecyclerView.Adapter<PlaylistAdapter.ListViewHolder>() {

    private lateinit var clickListener : ClickListener
    private var item = mutableListOf<PlaylistItem>()
    private var isPlayList = mutableListOf<Boolean>()
    private var mContext = context

    interface ClickListener {
        fun onClicked (view : View, position : Int)
    }

    fun clickListener (clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class ListViewHolder (private val binding : ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : PlaylistItem) {
            binding.playlistTitle.text = item.title
            binding.playlistSinger.text = item.singer
            Glide.with(mContext).load(item.album)
                .into(binding.playlistImg)
            binding.playlistImg.clipToOutline = true
            if(item.isPlaying) {
                binding.playAnimation.visibility = View.VISIBLE
                binding.playAnimation.playAnimation()
                binding.playAnimation.repeatCount = LottieDrawable.INFINITE
            } else {
                binding.playAnimation.visibility = View.GONE
                binding.playAnimation.cancelAnimation()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemPlaylistBinding>(layoutInflater, R.layout.item_playlist, parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(item[position])
        holder.itemView.setOnClickListener {
            clickListener.onClicked(it, position)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun setItem (mItem : MutableList<PlaylistItem>) {
        item = mItem
        notifyDataSetChanged()
    }

    fun setIsPlay(item : MutableList<Boolean>) {
        isPlayList = item
        notifyDataSetChanged()
    }

}