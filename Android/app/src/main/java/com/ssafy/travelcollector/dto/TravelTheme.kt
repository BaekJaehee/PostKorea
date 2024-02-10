package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class TravelTheme(
    @SerializedName("themeId") val id: Int = 0,
    @SerializedName("themeName") val title: String = "",
    @SerializedName("description") val description: String = "",
    var heritageDetailList: List<Heritage> = listOf(),
    var isBookMarked: Boolean = false
)
