package com.ssafy.travelcollector.util

import com.ssafy.travelcollector.api.UserService
import com.ssafy.travelcollector.config.ApplicationClass

class RetrofitUtil {
    companion object{
        val USER_SERVICE: UserService = ApplicationClass.retrofit.create(UserService::class.java)
    }
}