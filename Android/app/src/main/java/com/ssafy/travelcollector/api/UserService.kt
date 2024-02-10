package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.RequestPassword
import com.ssafy.travelcollector.dto.RequestUserInfo
import com.ssafy.travelcollector.dto.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserService {
    @POST("member/signup")
    suspend fun insert(@Body body: User): Response<HashMap<String, Any>>
    @POST("member/login")
    suspend fun login(@Body body: User): Response<HashMap<String, Any>>

    @GET("member")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<User>

    @PUT("member/edit-password")
    suspend fun checkPwd(@Header("Authorization") token: String, @Body check: RequestPassword): Response<Any>

    @PUT("member/edit-password")
    suspend fun editPwd(@Header("Authorization") token: String, @Body pwd: RequestPassword): Response<Any>

    @PUT("member/edit")
    suspend fun editUserInfo(@Header("Authorization") token: String, @Body info: RequestUserInfo): Response<Any>

    @Multipart
    @POST("member/profile/image")
    suspend fun uploadProfileImage(@Header("Authorization") token: String, @Part img:MultipartBody.Part): Response<Any>

    @GET("member/manage/{memberId}")
    suspend fun getUserInfoById(@Path("memberId") id: Int): Response<User>


}