package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Achievement(
    @SerializedName("achieveId") val id: Int,
    @SerializedName("achieveName") val name: String,
    @SerializedName("achieveDesc") val description: String,
)
