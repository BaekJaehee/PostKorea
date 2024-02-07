package com.ssafy.travelcollector.fragment.travel

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.ImageSliderAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPostEditBinding
import com.ssafy.travelcollector.util.GalleryLauncher
import com.ssafy.travelcollector.util.UriPartConverter
import okhttp3.MultipartBody

private const val TAG = "TravelPostEditFragment"
class TravelPostEditFragment : BaseFragment<FragmentTravelPostEditBinding>(FragmentTravelPostEditBinding::bind, R.layout.fragment_travel_post_edit) {

    private val imageAdapter: ImageSliderAdapter by lazy{
        ImageSliderAdapter()
    }

    private val galleryLauncher: GalleryLauncher by lazy{
        GalleryLauncher(this)
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
        galleryLauncher.pictureCallbackListener = object : GalleryLauncher.PictureCallbackListener{
            override fun onGetData(data: Uri) {
                val newList = imageAdapter.currentList.toMutableList()
                newList.add(data)
                imageAdapter.submitList(newList)
            }
        }
        binding.travelPostEditBtnAddPicture.setOnClickListener{
            galleryLauncher.launch()
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

        binding.travelPlanBtnSave.setOnClickListener {
            if(binding.travelPostEditEtTitle.text.isEmpty() || binding.travelPostEditEtContent.text.isEmpty()){
                showToast("내용을 입력해주세요")
            }else{
                val images = arrayListOf<MultipartBody.Part>()
                for(img in imageAdapter.currentList){
                    images.add(UriPartConverter.convertedPart(img, requireContext()))
                }
                boardViewModel.postBoard(
                    binding.travelPostEditEtTitle.text.toString(),
                    binding.travelPostEditEtContent.text.toString(),
                    images
                )
                findNavController().popBackStack()
            }
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val newList = imageAdapter.currentList.toMutableList()
        newList.removeAt(item.itemId)
        imageAdapter.submitList(newList)
        return true
    }

}