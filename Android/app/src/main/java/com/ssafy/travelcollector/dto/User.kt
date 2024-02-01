package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

//  @SerializedName("user_email")
//  @SerializedName("user_pws")
//  @SerializedName("user_name")
// @SerializedName("user_nickname")
//  @SerializedName("user_age")
//  @SerializedName("user_gender")
// @SerializedName("user_auth")

enum class OAuthInfo{
    NONE, KAKAO, NAVER, GOOGLE
}

data class User(
    var userEmail: String = "",
    var userPwd: String = "",
    var userName: String = "1",
    var userNickname: String = "1",
    var userAge: Int = 0,
    var userGender: String = "string",
    var userAuth: OAuthInfo = OAuthInfo.NONE
)
