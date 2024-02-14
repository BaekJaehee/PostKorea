package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class RequestUserInfo(
    @SerializedName("member_name") val nickname: String,
    @SerializedName("member_age") val age: Int
){
    constructor(nickName: String):this(nickName, 0)
}
