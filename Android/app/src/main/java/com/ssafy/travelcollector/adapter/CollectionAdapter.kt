package com.ssafy.travelcollector.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.CollectionRvItemBinding
import com.ssafy.travelcollector.dto.Product


private const val TAG = "CollectionAdapter"
class CollectionAdapter : BaseAdapter<Product>(){

    private var selectedIdx: Int = -1

    fun setSelectIdx(idx: Int){
        selectedIdx = idx
    }

    inner class CollectionHolder(private val binding: CollectionRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data:Product) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.collectionIvImage)
            binding.collectionTvName.text = data.name
            if(data.date==null){
                binding.collectionTvName.background.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {setSaturation(0F)})
                binding.collectionIvImage.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {setSaturation(0F)})
                binding.collectionIvImage.background.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {setSaturation(0F)})
            }else{
                binding.collectionTvName.background.colorFilter = null
                binding.collectionIvImage.colorFilter = null
                binding.collectionIvImage.background.colorFilter = null
            }
            binding.root.setOnClickListener {
                clickListener.onClick(data, layoutPosition)
            }
            binding.root.setBackgroundColor(binding.root.resources.getColor(
                if(selectedIdx==layoutPosition) R.color.gold else com.kakao.sdk.friend.R.color.transparent)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return CollectionHolder(CollectionRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface ClickListener{
        fun onClick(data: Product, position: Int)
    }

    lateinit var clickListener: ClickListener
}