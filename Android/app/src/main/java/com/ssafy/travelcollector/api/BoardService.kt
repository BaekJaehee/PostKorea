package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Board
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface BoardService {
    @POST("boards")
    suspend fun postBoard(@Header("Authorization") token: String, @Body board: Board): Response<Board>

    @Multipart
    @POST("boards/{boardId}/image")
    suspend fun uploadProfileImage(@Path("boardId") id: Int, @Part images: List<MultipartBody.Part>): Response<Any>
}