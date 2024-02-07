package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class BoardImage(
    @SerializedName("imageId") val id: Int,
    @SerializedName("fileName") val name: String,
    @SerializedName("accessUrl") val url: String
)