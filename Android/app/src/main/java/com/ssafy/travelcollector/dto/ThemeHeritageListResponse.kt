package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class ThemeHeritageListResponse(
    @SerializedName("relicDetails") val heritageDetailList: List<Heritage>
)
