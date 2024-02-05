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
    @SerializedName("member_email") var memberEmail: String = "",
    @SerializedName("member_pwd") var memberPwd: String = "",
    @SerializedName("member_name") var userName: String = "1",
    @SerializedName("member_nickname") var userNickname: String = "1",
    @SerializedName("member_age") var userAge: Int = 0,
    @SerializedName("member_gender") var userGender: String = "string",
    @SerializedName("member_auth") var userAuth: OAuthInfo = OAuthInfo.NONE,
    @SerializedName("profileUrl") var profileUrl: String = ""
)
