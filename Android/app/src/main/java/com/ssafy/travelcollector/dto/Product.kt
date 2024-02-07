package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val id: Int,
    @SerializedName("productName") val name: String,
    @SerializedName("productImage") val image: String,
    @SerializedName("productPoint") val point: Int
){
    constructor():this(0, "", "", 0)
}
