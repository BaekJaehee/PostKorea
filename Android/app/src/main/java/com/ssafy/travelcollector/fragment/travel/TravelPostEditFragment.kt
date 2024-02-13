package com.ssafy.travelcollector.fragment.travel

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.ImageSliderAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPostEditBinding
import com.ssafy.travelcollector.util.GalleryLauncher
import com.ssafy.travelcollector.util.UriPartConverter
import com.ssafy.travelcollector.viewModel.BoardViewModel
import okhttp3.MultipartBody

private const val TAG = "TravelPostEditFragment"
class TravelPostEditFragment : BaseFragment<FragmentTravelPostEditBinding>(FragmentTravelPostEditBinding::bind, R.layout.fragment_travel_post_edit) {

    private val imageAdapter: ImageSliderAdapter by lazy{
        ImageSliderAdapter()
    }

    private val galleryLauncher: GalleryLauncher by lazy{
        GalleryLauncher(this)
    }

    private val tags = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initView()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initView(){
        if(boardViewModel.isTravelHeritageBoard.value){
            val placeTag = "#${heritageViewModel.curHeritage.value.name.replace(" ", "")} "
            binding.travelPostEditEtContent.apply{
                setText(placeTag)
                addTextChangedListener{
                    doAfterTextChanged {
                        if(it!!.length<placeTag.length){
                            binding.travelPostEditEtContent.apply {
                                setText(placeTag)
                                setSelection(placeTag.length)
                            }
                        }

                    }
                }
            }

        }

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

                val words = binding.travelPostEditEtContent.text.toString().trim().split("\n", " ")
                val tags = words.filter{it[0]=='#'}.toMutableList()
                tags.add("#email=${accountViewModel.user.value.memberEmail}")
                if(boardViewModel.isTravelHeritageBoard.value)
                    tags.add("#heritage=${heritageViewModel.curHeritage.value.id}")

                boardViewModel.postBoard(
                    binding.travelPostEditEtTitle.text.toString(),
                    binding.travelPostEditEtContent.text.toString(),
                    images,
                    tags.map { it.substring(1) }

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