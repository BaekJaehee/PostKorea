package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Achievement
import com.ssafy.travelcollector.dto.Heritage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Path
interface VisitService {

    @GET("visit/achieve")
    suspend fun getAchieve(@Header("Authorization") token: String, @Query("owned") owned: Boolean = true): Response<List<Achievement>>

    @POST("visit/{relicId}")
    suspend fun addVisitedHeritage(@Header("Authorization") token: String, @Path("relicId") id: Int): Response<Any>

    @PUT("visit/title/{achieveId}")
    suspend fun useTitle(@Header("Authorization") token: String, @Path("achieveId") id: Int): Response<Any>

    @GET("visit/list")
    suspend fun getVisited(@Header("Authorization") token: String): Response<List<Heritage>>

}