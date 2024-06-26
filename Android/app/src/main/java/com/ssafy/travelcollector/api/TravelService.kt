package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.TravelPlanResponse
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelService {
    @POST("explore-plans")
    suspend fun planTravel(@Header("Authorization") token: String, @Body travel: TravelWithHeritageList): Response<TravelPlanResponse>

    @POST("explore-plans/manage/{planId}/bulk-add")
    suspend fun addHeritageListToTravelPlan(
        @Header("Authorization") token: String,
        @Path("planId") travelId: Int,
        @Body travelList: List<Int>
    ): Response<Any>

    @HTTP(method = "DELETE", path = "explore-plans/manage/{planId}/bulk-delete", hasBody = true)
    suspend fun deleteHeritageListToTravelPlan(
        @Header("Authorization") token: String,
        @Path("planId") travelId: Int,
        @Body travelList: List<Int>
    ): Response<Any>

    @PUT("explore-plans/manage/{planId}/bulk-update")
    suspend fun updateVisitedHeritageListToTravelPlan(
        @Header("Authorization") token: String,
        @Path("planId") travelId: Int,
        @Query("visited") visited: Boolean,
        @Body travelList: List<Int>
    )

    @PUT("explore-plans/{planId}")
    suspend fun updateTravel(
        @Header("Authorization") token: String,
        @Path("planId") travelId: Int,
        @Body travel: TravelWithHeritageList
    ): Response<Any>


    @GET("explore-plans/upcoming")
    suspend fun getUpcomingTravelList(@Header("Authorization") token: String): Response<List<TravelPlanResponse>>

    @GET("explore-plans/ongoing")
    suspend fun getOngoingTravelList(@Header("Authorization") token: String): Response<List<TravelPlanResponse>>

    @GET("explore-plans/completed")
    suspend fun getCompletedTravelList(@Header("Authorization") token: String): Response<List<TravelPlanResponse>>

    @GET("explore-plans/list/{planId}")
    suspend fun getHeritageListOfTravel(
        @Header("Authorization") token: String,
        @Path("planId") travelId: Int
    ): Response<List<Heritage>>

    @DELETE("explore-plans/{planId}")
    suspend fun deleteTravel(@Header("Authorization") token: String, @Path("planId") travelId: Int): Response<Any>

}