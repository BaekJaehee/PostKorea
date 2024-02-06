package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Board(
    @SerializedName("boardId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("memberId") val writer: Int,
    @SerializedName("createdate") val date: Long,
    @SerializedName("comments") val comments: List<Comment>,
    @SerializedName("imageUrls") val images: List<String>
){
    constructor(title: String, content:String): this(
        id = 0,
        title = title,
        content = content,
        writer = 0,
        date = 0,
        comments = listOf(),
        images = listOf()
    )
}
