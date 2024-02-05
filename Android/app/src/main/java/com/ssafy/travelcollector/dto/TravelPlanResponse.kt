package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class TravelPlanResponse(
    @SerializedName("planId") val planId: Int,
    @SerializedName("memberId") val id: Int,
    @SerializedName("planName") val name: String,
    @SerializedName("planStartDate") val startDate: Long,
    @SerializedName("planEndDate") val endDate: Long,
    @SerializedName("planCondition") val condition: Boolean
)
