package com.ssafy.travelcollector.fragment.travel

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.ImageSliderAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPostEditBinding
import com.ssafy.travelcollector.test.TestAdapter

private const val TAG = "TravelPostEditFragment"
class TravelPostEditFragment : BaseFragment<FragmentTravelPostEditBinding>(FragmentTravelPostEditBinding::bind, R.layout.fragment_travel_post_edit) {

    private val imageAdapter: ImageSliderAdapter by lazy{
        ImageSliderAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initView(){
        binding.heritagePostVp2.offscreenPageLimit = 1
        binding.heritagePostVp2.adapter = imageAdapter
        binding.travelPostEditBtnAddPicture.setOnClickListener{
            requestPermissionLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
    }

    private fun initAdapter(){
        imageAdapter.imageBinder = object : ImageSliderAdapter.ImageBinder{
            override fun imageBind(url: String, imageView: ImageView) {
                Glide.with(requireContext())
                    .load(url)
                    .into(imageView)
                binding.heritagePostVp2.setCurrentItem(imageAdapter.itemCount-1, true)
            }
        }
    }

    // 갤러리 open
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
           for(entry in it.entries) {
               if (!entry.value) {
                   Log.d(TAG, ": denied")
                   return@registerForActivityResult
               }
           }
            openGallery()
        }
    // 가져온 사진 보여주기
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    val newList = imageAdapter.currentList.toMutableList()
                    newList.add(it.toString())
                    imageAdapter.submitList(newList)
                }
            }
        }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val newList = imageAdapter.currentList.toMutableList()
        newList.removeAt(item.itemId)
        imageAdapter.submitList(newList)
        return true
    }

    private fun openGallery(){
        pickImageLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
    }
}