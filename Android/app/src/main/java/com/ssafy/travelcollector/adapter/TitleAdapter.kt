package com.ssafy.travelcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.TitleRvItemBinding
import com.ssafy.travelcollector.dto.Achievement

class TitleAdapter: BaseAdapter<Achievement>() {

    private var selectedIdx: Int = -1

    fun setSelectIdx(idx: Int){
        selectedIdx = idx
    }

    inner class TitleHolder(private val binding:TitleRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data: Achievement) {
            binding.titleTvTitle.text = data.name
            binding.root.setOnClickListener {
                eventListener.onClick(data.name, data.description, layoutPosition)
            }
            binding.titleTvTitle.setTextColor(binding.root.resources.getColor(
                if(selectedIdx==layoutPosition) R.color.gold else R.color.black)
            )
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return TitleHolder(TitleRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface EventListener{
        fun onClick(title: String, desc: String, position: Int)
    }

    lateinit var eventListener: EventListener
}