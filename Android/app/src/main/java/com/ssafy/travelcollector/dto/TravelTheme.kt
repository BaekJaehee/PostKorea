package com.ssafy.travelcollector.dto

data class TravelTheme(
    var id: Int = 0,
    var title: String = "",
    var contents: ArrayList<Heritage> = arrayListOf(),
    var isBookMarked: Boolean = false
)
