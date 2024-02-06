package com.ssafy.travelcollector.dto

data class Comment(
    var id: Int = 0,
    var date: Long = 0,
    var content: String = "",
    var writerName: String = "",
    var writerProfile: String = ""
)
