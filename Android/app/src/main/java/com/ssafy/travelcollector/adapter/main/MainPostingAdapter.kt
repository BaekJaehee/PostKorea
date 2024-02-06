package com.ssafy.travelcollector.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.MainPostingRvItemBinding
import com.ssafy.travelcollector.databinding.TestBinding
import com.ssafy.travelcollector.dto.Posting

class MainPostingAdapter: BaseAdapter<Posting>() {

    inner class MainPostingHolder(private val binding: MainPostingRvItemBinding): BaseHolder(binding) {
        override fun bindInfo(data: Posting) {
            binding.mainPostingTitle.text = data.postTitle
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = MainPostingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainPostingHolder(binding).apply{
            binding.root.setOnClickListener{
                clickListener.onClick(layoutPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface IClickListener{
        fun onClick(position: Int)
    }

    lateinit var clickListener: IClickListener

}