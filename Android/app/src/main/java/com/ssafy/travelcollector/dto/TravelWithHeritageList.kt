package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class TravelWithHeritageList(
    var id: Int = -1,
    @SerializedName("plan_name") var name: String = "",
    @SerializedName("plan_start_date") var startDate: Long = 0,
    @SerializedName("plan_end_date") var endDate: Long = 0,
    @SerializedName("plan_condition") var condition: Boolean = true,
    val heritageList: ArrayList<Heritage> = arrayListOf()
)
