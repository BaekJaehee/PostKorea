package com.ssafy.travelcollector.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.ThemeRvItemBinding
import com.ssafy.travelcollector.dto.TravelTheme

private const val TAG = "ThemeAdapter"
class ThemeAdapter : BaseAdapter<TravelTheme>() {

    inner class TravelThemHolder(private val binding: ThemeRvItemBinding): BaseHolder(binding){
        override fun bindInfo(data: TravelTheme) {
            binding.themeRvItemTitle.text = data.title
            binding.themeRvItemHeartClicked.visibility = if(data.isBookMarked) View.VISIBLE else View.GONE
            binding.themeRvItemHeartUnclicked.visibility = if(data.isBookMarked) View.GONE else View.VISIBLE

            binding.root.setOnClickListener{
                clickListener.onItemClick(layoutPosition)
            }
            binding.themeRvItemHeartClicked.setOnClickListener{
                clickListener.onBookMarkClick(layoutPosition)
            }
            binding.themeRvItemHeartUnclicked.setOnClickListener{
                clickListener.onBookMarkClick(layoutPosition)
            }
            binding.themeRvItemImages.setImages()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = ThemeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelThemHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface ClickListener{
        fun onItemClick(position: Int)
        fun onBookMarkClick(position: Int)
    }
    lateinit var clickListener: ClickListener
}