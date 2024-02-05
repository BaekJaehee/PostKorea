package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.TravelPlanResponse
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TravelService {
    @POST("explore-plans")
    suspend fun planTravel(@Header("Authorization") token: String, @Body travel: TravelWithHeritageList): Response<TravelPlanResponse>

    @POST("explore-plans/manage/{planId}/bulk-add")
    suspend fun addHeritageListToTravelPlan(
        @Header("Authorization") token: String,
        @Path("planId") travelId: Int,
        @Body travelList: List<Int>
    )

}