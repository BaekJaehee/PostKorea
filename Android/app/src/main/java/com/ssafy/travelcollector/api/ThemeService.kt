package com.ssafy.travelcollector.api

import com.ssafy.travelcollector.dto.ThemeHeritageListResponse
import com.ssafy.travelcollector.dto.TravelTheme
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ThemeService {

    @GET("themes")
    suspend fun getThemes(): Response<List<TravelTheme>>

    @GET("themes/{themeId}")
    suspend fun getThemeDetail(@Path("themeId") id: Int): Response<ThemeHeritageListResponse>

}