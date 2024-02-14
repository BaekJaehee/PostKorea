package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Heritage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HeritageService {

    @GET("relic/list")
    suspend fun getHeritageList(): Response<List<Heritage>>

    @GET("relic/detail/{id}")
    suspend fun getHeritageDetail(@Path("id") id: Int): Response<Heritage>

    @GET("relic/search")
    suspend fun searchHeritage(
        @Query("region1") region1: String?,
        @Query("region2") region2: String?,
        @Query("era") era: String?,
        @Query("category") category: String?
    ): Response<List<Heritage>>

    @GET("relic/search")
    suspend fun searchHeritageForGame(
        @Query("category") category: String?,
        @Query("limit") limit: Int?,
    ): Response<List<Heritage>>

    @GET("relic/random")
    suspend fun searchHeritageRandom(
        @Query("region1") region1: String?,
        @Query("region2") region2: String?,
        @Query("era") era: String?,
        @Query("category") category: String?
    ): Response<List<Heritage>>

    @GET("relic/find")
    suspend fun searchHeritageByName(
        @Query("name") name: String?,
    ): Response<List<Heritage>>

    @PUT("relic/point")
    suspend fun editPoints(@Header("Authorization") token: String, @Body points: Int): Response<Any>
}