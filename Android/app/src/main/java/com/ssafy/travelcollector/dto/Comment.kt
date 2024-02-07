package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("commentId") var id: Int = 0,
    @SerializedName("createdDate") var date: Long = 0,
    @SerializedName("content") var content: String = "",
    @SerializedName("memberId") var writerName: Int = 0,
    @SerializedName("boardId") var boardId: Int = 0
)
