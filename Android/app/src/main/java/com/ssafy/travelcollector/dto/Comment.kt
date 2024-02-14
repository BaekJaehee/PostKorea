package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("commentId") var id: Int = 0,
    @SerializedName("createdDate") var date: Long = 0,
    @SerializedName("content") var content: String = "",
    @SerializedName("memberId") var writerId: Int = 0,
    @SerializedName("boardId") var boardId: Int = 0,
    @SerializedName("memberProfileUrl") var imgUrl: String? = "",
    @SerializedName("memberName") var writerName: String = "",
    @SerializedName("memberAchieve") var writerTitle: String? = ""
)
