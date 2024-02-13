package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Heritage(
    var no: Int = 0,
    @SerializedName("relicId") var id: Int = 0,
    @SerializedName("ccbaMnm1") var name: String = "",
    @SerializedName("imageUrl") var imageUrl: String = "",
    @SerializedName("content") var content: String = "",
    @SerializedName("latitude") var lat: String = "",
    @SerializedName("longitude") var lng: String = "",
    @SerializedName("mcodeName") var category: String = "",
    var isBookMarked: Boolean = false,
    val gameEnable: Boolean = false
)
