package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Board
import com.ssafy.travelcollector.dto.Comment
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    @POST("boards")
    suspend fun postBoard(@Header("Authorization") token: String, @Body board: Board): Response<Board>

    @Multipart
    @POST("boards/{boardId}/image")
    suspend fun uploadProfileImage(@Path("boardId") id: Int, @Part images: List<MultipartBody.Part>): Response<Any>

    @GET("boards")
    suspend fun getAllBoards(): Response<List<Board>>

    @GET("boards/{boardId}")
    suspend fun getBoardDetail(@Path("boardId") id: Int): Response<Board>

    @POST("boards/{boardId}/comments")
    suspend fun postComment(@Header("Authorization") token: String, @Path("boardId") id: Int, @Body comment: Comment): Response<Comment>

    @GET("boards/{boardId}/comments")
    suspend fun getComments(@Path("boardId") id: Int): Response<List<Comment>>

    @DELETE("boards/{boardId}/comments/{commentId}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("boardId") bId: Int,
        @Path("commentId") cId: Int
    ): Response<Any>

    @DELETE("boards/{boardId}")
    suspend fun deleteBoard(@Header("Authorization") token: String, @Path("boardId") bId: Int): Response<Any>

    @GET("boards/searchByTags")
    suspend fun searchByTag(@Query("tags") tags: List<String>): Response<List<Board>>

    @GET("boards/searchKeyword")
    suspend fun searchByKeyword(@Query("keyword") keyword: String): Response<List<Board>>

}