package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Heritage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeritageService {

    @GET("relic/list")
    suspend fun getHeritageList(): Response<List<Heritage>>

    @GET("relic/detail/{id}")
    suspend fun getHeritageDetail(@Path("id") id: Int): Response<Heritage>

}