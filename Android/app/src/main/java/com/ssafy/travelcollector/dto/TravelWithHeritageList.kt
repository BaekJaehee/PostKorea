package com.ssafy.travelcollector.dto

data class TravelWithHeritageList(
    var id: Int = -1,
    var name: String = "",
    var startDate: Long = 0,
    var endDate: Long = 0,
    //가기 전 0, 진행 중 1, 끝 2
    var condition: Int = 0,
    val heritageList: ArrayList<Heritage> = arrayListOf()
)
