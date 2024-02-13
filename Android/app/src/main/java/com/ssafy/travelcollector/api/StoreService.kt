package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StoreService {
    @GET("shop/product/available")
    suspend fun getProducts(): Response<List<Product>>

    @POST("shop/purchase/{productId}")
    suspend fun purchaseProduct(@Header("Authorization") token: String, @Path("productId") id: Int): Response<Any>

    @GET("shop/collect")
    suspend fun getCollection(@Header("Authorization") token: String): Response<List<Product>>

}