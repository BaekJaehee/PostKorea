package com.ssafy.travelcollector.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UriPartConverter {
    companion object{
        private fun absolutelyPath(path: Uri?, context : Context): String {
            var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
            var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
            var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            c?.moveToFirst()
            var result = c?.getString(index!!)
            return result!!
        }
        fun convertedPart(data: Uri, context: Context): MultipartBody.Part{
            val file = File(absolutelyPath(data, context))
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData("file", file.name, requestFile)
        }
    }



}