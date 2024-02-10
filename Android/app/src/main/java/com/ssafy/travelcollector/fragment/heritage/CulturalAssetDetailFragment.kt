package com.ssafy.travelcollector.fragment.heritage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentCulturalAssetDetailBinding
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import kotlinx.coroutines.launch

class CulturalAssetDetailFragment : BaseFragment<FragmentCulturalAssetDetailBinding>(FragmentCulturalAssetDetailBinding::bind,
    R.layout.fragment_cultural_asset_detail
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){

        lifecycleScope.launch{
            launch {
               heritageViewModel.curHeritage.collect{
                    binding.culturalAssetDetailTvName.text = it.name
                    Glide.with(requireContext())
                        .load(it.imageUrl)
                        .fitCenter()
                        .into(binding.culturalAssetDetailIv)
                    binding.culturalAssetDetailTvDescription.text = it.content
                   if(it.gameEnable){
                       mainActivityViewModel.addDetailState(arrayListOf(DetailStateEnum.MiniGame))
                   }
                }
            }
            launch {
                mainActivityViewModel.detailState.collect{
                    if(it.contains(DetailStateEnum.MiniGame)){
                        binding.culturalAssetDetailBtnGame.visibility = View.VISIBLE
                        binding.culturalAssetDetailBtnAddToTravel.visibility = View.GONE
                    }else if(it.contains(DetailStateEnum.AddToTravel)){
                        binding.culturalAssetDetailBtnGame.visibility = View.GONE
                        binding.culturalAssetDetailBtnAddToTravel.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.culturalAssetDetailBtnAddToTravel.setOnClickListener {
            travelViewModel.addHeritageToTravelPlan(heritageViewModel.curHeritage.value)
            findNavController().popBackStack(R.id.travelPlanFragment, false)
        }

        binding.culturalAssetDetailBtnNewRecord.setOnClickListener {

            //태그 기능 백엔드에 적용되면 태그 추가 필요

            findNavController().navigate(R.id.travelPostEditFragment)
        }

    }
}