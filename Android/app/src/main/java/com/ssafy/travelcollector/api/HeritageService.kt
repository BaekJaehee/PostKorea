package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Heritage
import retrofit2.Response
import retrofit2.http.GET

interface HeritageService {

    @GET("relic/list")
    suspend fun getHeritageList(): Response<List<Heritage>>

}