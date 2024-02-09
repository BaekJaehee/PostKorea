package com.ssafy.travelcollector

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentProfileBinding
import com.ssafy.travelcollector.util.GalleryLauncher
import com.ssafy.travelcollector.util.RetrofitUtil
import com.ssafy.travelcollector.util.UriPartConverter
import com.ssafy.travelcollector.viewModel.AccountViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {

    private val galleryLauncher: GalleryLauncher by lazy{
        GalleryLauncher(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){
        galleryLauncher.pictureCallbackListener = object : GalleryLauncher.PictureCallbackListener{
            override fun onGetData(data: Uri) {
                Glide.with(requireContext())
                    .load(data)
                    .into(binding.profileImage)
                lifecycleScope.launch {
                    accountViewModel.editProfileImg(data, requireContext())
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    accountViewModel.user.collect{
                        Glide.with(requireContext())
                            .load(it.profileUrl)
                            .into(binding.profileImage)
                    }
                }
            }
        }

        binding.profileCamera.setOnClickListener{
            galleryLauncher.launch()
        }
        binding.profileChangePersonalInfo.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeUserInfoFragment)
        }

        binding.profileAchievement.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_titleFragment)
        }

    }


}