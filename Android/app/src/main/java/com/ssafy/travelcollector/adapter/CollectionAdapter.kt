package com.ssafy.travelcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.CollectionRvItemBinding
import com.ssafy.travelcollector.dto.Product

class CollectionAdapter : BaseAdapter<Product>(){

    inner class CollectionHolder(private val binding: CollectionRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data:Product) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.collectionIvImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return CollectionHolder(CollectionRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }
}