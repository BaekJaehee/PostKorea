package com.ssafy.travelcollector.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

private const val TAG = "GalleryLauncher"
class GalleryLauncher(fragment: Fragment) {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun launch(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU){
            openGallery()
        } else
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    // 갤러리 open
    private val requestPermissionLauncher =
       fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            for(entry in it.entries) {
                if (!entry.value) {
                    Log.d(TAG, ": denied $entry")
                    return@registerForActivityResult
                }
            }
            openGallery()
        }

    private fun openGallery(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU) {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }else{
            pickImageLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
        }

    }

    private val pickMedia = fragment.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            pictureCallbackListener.onGetData(uri)
        }
    }

    // 가져온 사진 보여주기
    private val pickImageLauncher: ActivityResultLauncher<Intent> = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    pictureCallbackListener.onGetData(it)
                }
            }
        }

    interface PictureCallbackListener{
        fun onGetData(data: Uri)
    }

    lateinit var pictureCallbackListener: PictureCallbackListener


}