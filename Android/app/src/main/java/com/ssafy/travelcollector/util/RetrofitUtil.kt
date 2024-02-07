package com.ssafy.travelcollector.util

import com.ssafy.travelcollector.api.BoardService
import com.ssafy.travelcollector.api.HeritageService
import com.ssafy.travelcollector.api.TravelService
import com.ssafy.travelcollector.api.UserService
import com.ssafy.travelcollector.config.ApplicationClass

class RetrofitUtil {
    companion object{
        val USER_SERVICE: UserService = ApplicationClass.retrofit.create(UserService::class.java)
        val HERITAGE_SERVICE: HeritageService = ApplicationClass.retrofit.create(HeritageService::class.java)
        val TRAVEL_SERVICE: TravelService = ApplicationClass.retrofit.create(TravelService::class.java)
        val BOARD_SERVICE: BoardService = ApplicationClass.retrofit.create(BoardService::class.java)
    }
}