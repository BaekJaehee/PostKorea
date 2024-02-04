package com.ssafy.travelcollector.fragment.heritage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
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

        binding.culturalAssetDetailBtnAddToTravel.setOnClickListener {
            travelViewModel.addHeritageToTravelPlan(mainActivityViewModel.curHeritage.value)
            parentFragmentManager.popBackStack()
        }

    }
}