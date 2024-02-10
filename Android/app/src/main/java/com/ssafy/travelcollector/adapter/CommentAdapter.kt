package com.ssafy.travelcollector.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.CommentRvItemBinding
import com.ssafy.travelcollector.dto.Comment
import com.ssafy.travelcollector.util.TimeConverter

private const val TAG = "CommentAdapter"
class CommentAdapter: BaseAdapter<Comment> (){

    inner class CommentHolder(private val binding: CommentRvItemBinding): BaseHolder(binding), View.OnCreateContextMenuListener{
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: Comment) {
            binding.comment.text = data.content
            binding.date.text = TimeConverter.timeMilliToDateString(data.date)
            binding.root.setOnCreateContextMenuListener(this)
            Glide.with(binding.root)
                .load(data.imgUrl)
                .into(binding.boardDetailProfileImg)
            binding.userName.text = "${data.writerTitle?:""} ${data.writerName}"
            Log.d(TAG, "bindInfo: ${data.writerTitle}")
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val menuItem = menu.add(0,0,0,"삭제하기")
            menuItem.setOnMenuItemClickListener {
                eventListener.delete(layoutPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = CommentRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface EventListener{
        fun delete(position: Int)
    }

    lateinit var eventListener:EventListener
}