package com.ssafy.travelcollector.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.ImageView
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.PostVp2SliderItemBinding

class ImageSliderAdapter:BaseAdapter<String>() {

    inner class ImageHolder(private val binding: PostVp2SliderItemBinding): BaseHolder(binding), OnCreateContextMenuListener {
        override fun bindInfo(url: String) {
            imageBinder.imageBind(url, binding.imageSlider)
            binding.imageSlider.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(0, adapterPosition, 0, "삭제")
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = PostVp2SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface ImageBinder{
        fun imageBind(url: String, imageView: ImageView)
    }
    lateinit var imageBinder: ImageBinder
}