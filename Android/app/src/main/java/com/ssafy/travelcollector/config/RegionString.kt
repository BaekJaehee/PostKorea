package com.ssafy.travelcollector.config

class RegionString(
    val region: String,
    val subRegion: List<String>,
) {

    companion object{
        fun findByName(name: String): List<String>{
            val res = listOf<String>()
            for(rs in REGION){
                if(rs.region == name)
                    return rs.subRegion
            }
            return res
        }

        val REGION = listOf(
            RegionString(
                "서울특별시",
                listOf("강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구")
            ),
            RegionString(
                "부산광역시",
                listOf("강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군")
            ),
            RegionString(
                "인천광역시",
                listOf("계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군")
            ),
            RegionString(
                "대구광역시",
                listOf("남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군")
            ),
            RegionString(
                "울산광역시",
                listOf("남구", "동구", "북구", "중구", "울주군")
            )
        )
    }

}