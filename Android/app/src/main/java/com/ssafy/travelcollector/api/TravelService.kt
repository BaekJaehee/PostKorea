package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.TravelWithHeritageList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelService {
    @POST("")
    suspend fun addTravelPlan(): Response<Any>
}