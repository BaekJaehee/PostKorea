package com.ssafy.travelcollector.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimeConverter {
    companion object{
        fun timeMilliToDateString(time: Long): String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            return SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(calendar.time).toString()
        }
    }
}