package com.ssafy.travelcollector.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.MainHeritageRvItemBinding
import com.ssafy.travelcollector.dto.Heritage

class MainHeritageAdapter: BaseAdapter<Heritage>() {

    inner class HeritageHolder(private val binding : MainHeritageRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data: Heritage) {
            binding.mainHeritageTvName.text = data.name
            Glide.with(binding.root)
                .load(data.imageUrl)
                .into(binding.mainHeritageCvImg)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return HeritageHolder(MainHeritageRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }
}