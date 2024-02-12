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
    @SerializedName("member_name") var userName: String = "",
    @SerializedName("member_nickname") var userNickname: String = "1",
    @SerializedName("member_age") var userAge: Int = 0,
    @SerializedName("member_gender") var userGender: String = "MALE",
    @SerializedName("member_auth") var userAuth: String = "NONE",
    @SerializedName("member_profile_url") var profileUrl: String = "",
    @SerializedName("member_role") var role: String = "MEMBER",
    @SerializedName("member_point") var point: Int = 0,
    @SerializedName("member_achieve") val title: String? = null
)
