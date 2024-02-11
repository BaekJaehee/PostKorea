package com.ssafy.travelcollector.fragment.account

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentProfileBinding
import com.ssafy.travelcollector.util.GalleryLauncher
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind,
    R.layout.fragment_profile
) {

    private val galleryLauncher: GalleryLauncher by lazy{
        GalleryLauncher(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    @SuppressLint("SetTextI18n")
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
                        binding.profileTvName.text = it.userName
                        binding.profileTvTitle.text = "-${it.title?:""}-"
                    }
                }
            }
        }

        binding.profileCamera.setOnClickListener{
            galleryLauncher.launch()
        }
        binding.profileBtnChangePersonalInfo.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeUserInfoFragment)
        }

        binding.profileBtnAchievement.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_titleFragment)
        }

        binding.profileBtnCollection.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_collectionFragment)
        }

    }


}