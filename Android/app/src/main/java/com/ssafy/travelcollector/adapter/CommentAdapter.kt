package com.ssafy.travelcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.CommentRvItemBinding
import com.ssafy.travelcollector.dto.Comment

class CommentAdapter: BaseAdapter<Comment> (){

    inner class CommentHolder(private val binding: CommentRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data: Comment) {
            binding.comment.text = data.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = CommentRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }
}