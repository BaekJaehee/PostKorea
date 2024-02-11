package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Achievement(
    @SerializedName("achieve_id") val id: Int,
    @SerializedName("achieve_name") val name: String,
    @SerializedName("achieve_desc") val description: String,
    @SerializedName("achieve_date") val date: Long,
    @SerializedName("achieve_relic_name") val place: String?
)
