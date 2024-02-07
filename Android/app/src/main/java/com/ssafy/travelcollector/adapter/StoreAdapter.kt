package com.ssafy.travelcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.StoreRvItemBinding
import com.ssafy.travelcollector.dto.Product

class StoreAdapter : BaseAdapter<Product>() {
    inner class ProductHolder(private val binding: StoreRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data: Product) {
            binding.productName.text = data.name
            binding.productPrice.text = data.point.toString()
            binding.btnPurchase.setOnClickListener {
                eventListener.onClick(layoutPosition)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return ProductHolder(StoreRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface EventListener{
        fun onClick(position: Int)
    }

    lateinit var eventListener: EventListener

}