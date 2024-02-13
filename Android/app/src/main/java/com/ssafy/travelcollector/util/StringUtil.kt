package com.ssafy.travelcollector.util

class StringUtil {
    companion object{
        fun nullableString(orig: String): String?{
            return orig.ifEmpty { null }
        }

        fun emailCheck(email: String): Boolean{
            val pattern = android.util.Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }
    }
}