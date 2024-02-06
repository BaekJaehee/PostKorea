package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class RequestPassword(
    @SerializedName("current_pwd") val curPwd: String,
    @SerializedName("new_pwd") val newPwd: String
){
    constructor(pwd: String) : this(pwd, pwd)
}
