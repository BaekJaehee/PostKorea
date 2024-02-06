package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Heritage
import retrofit2.Response
import retrofit2.http.GET
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
        @Query("ccceName") era: String?,
        @Query("mcodeName") category: String?
    ): Response<List<Heritage>>

}