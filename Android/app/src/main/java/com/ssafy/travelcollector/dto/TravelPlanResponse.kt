package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class TravelPlanResponse(
    @SerializedName("planId") val planId: Int,
    @SerializedName("memberId") val id: Int
)
