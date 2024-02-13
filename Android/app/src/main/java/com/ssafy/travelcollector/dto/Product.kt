package com.ssafy.travelcollector.dto

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val id: Int,
    @SerializedName("productName") val name: String,
    @SerializedName("productImage") val image: String,
    @SerializedName("productPoint") val point: Int,
    @SerializedName("productExplanation") val desc: String,
    @SerializedName("productDate") val date: Long?,
    @SerializedName("productAcquisition") val acquisition: String,
    var isPurchasable: Boolean = true
){
    constructor():this(0, "", "", 0, "", null, "")
}
