package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("member/signup")
    suspend fun insert(@Body body: User): Response<HashMap<String, Any>>
    @POST("member/login")
    suspend fun login(@Body body: User): Response<HashMap<String, Any>>

    @GET("member")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<HashMap<String, Any>>
}