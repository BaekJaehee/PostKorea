package com.ssafy.travelcollector.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.TravelListRvItemBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TravelAdapter: BaseAdapter<TravelWithHeritageList>() {

    inner class TravelHolder(private val binding: TravelListRvItemBinding): BaseHolder(binding){
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: TravelWithHeritageList) {
            binding.mainPostingTitle.text = data.name
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = data.startDate
            val startDate = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(calendar.time).toString()
            calendar.timeInMillis = data.endDate
            val endDate = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(calendar.time).toString()
            binding.travelListRvItemTvDuration.text =
                "$startDate ~ $endDate"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = TravelListRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

}