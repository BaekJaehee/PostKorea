package com.ssafy.travelcollector.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.TravelListRvItemBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.util.TimeConverter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TravelAdapter: BaseAdapter<TravelWithHeritageList>() {

    inner class TravelHolder(private val binding: TravelListRvItemBinding): BaseHolder(binding){
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: TravelWithHeritageList) {
            binding.mainPostingTitle.text = data.name
            val startDate = TimeConverter.timeMilliToDateString(data.startDate)
            val endDate = TimeConverter.timeMilliToDateString(data.endDate)
            binding.travelListRvItemTvDuration.text =
                "$startDate ~ $endDate"
            binding.travelListOiImg.setImages(ArrayList(data.heritageList.map{it.imageUrl}))

            binding.root.setOnClickListener{
                clickListener.onClick(layoutPosition, data.condition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = TravelListRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface ClickListener{
        fun onClick(position: Int, state: Boolean)
    }
    lateinit var clickListener: ClickListener

}