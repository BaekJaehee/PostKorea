package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Heritage(
    var no: Int = 0,
    @SerializedName("relicId") var id: Int = 0,
    @SerializedName("ccbaMnm1") var name: String = "",
    @SerializedName("imageUrl") var imageUrl: String = "",
    var isBookMarked: Boolean = false
)
