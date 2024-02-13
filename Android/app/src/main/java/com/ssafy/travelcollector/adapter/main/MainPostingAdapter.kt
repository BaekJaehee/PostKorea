package com.ssafy.travelcollector.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.MainPostingRvItemBinding
import com.ssafy.travelcollector.dto.Board

class MainPostingAdapter: BaseAdapter<Board>() {

    inner class MainPostingHolder(private val binding: MainPostingRvItemBinding): BaseHolder(binding) {
        override fun bindInfo(data: Board) {
            binding.mainPostingTitle.text = data.title
            binding.root.setOnClickListener {
                clickListener.onClick(data.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = MainPostingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainPostingHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface IClickListener{
        fun onClick(id: Int)
    }

    lateinit var clickListener: IClickListener

}