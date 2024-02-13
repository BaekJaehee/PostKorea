package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class BoardTagRequestQuery(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("size") val size: Int = 1,
    @SerializedName("sort") val sort: List<String> = listOf("tag.tagName,asc")
)
