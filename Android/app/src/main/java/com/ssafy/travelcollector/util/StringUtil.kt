package com.ssafy.travelcollector.util

class StringUtil {
    companion object{
        fun nullableString(orig: String): String?{
            return orig.ifEmpty { null }
        }
    }
}