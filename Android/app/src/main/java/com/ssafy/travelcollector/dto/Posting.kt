package com.ssafy.travelcollector.dto

import java.util.Date

data class Posting(
    val postId: Int = 0,
    val userId: Int = 0,
    val postTitle: String = "title",
    val postContent: String = "content",
    val postImg: String = "url",
    val postCreated: Long = 0,
    val postTags: ArrayList<Int> = arrayListOf()
)
