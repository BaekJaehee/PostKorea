package com.ssafy.travelcollector.fragment.heritage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kakao.sdk.navi.Constants
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.Location
import com.kakao.sdk.navi.model.NaviOption
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentCulturalAssetDetailBinding
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import kotlinx.coroutines.launch

private const val TAG = "CulturalAssetDetailFrag"
class CulturalAssetDetailFragment : BaseFragment<FragmentCulturalAssetDetailBinding>(FragmentCulturalAssetDetailBinding::bind,
    R.layout.fragment_cultural_asset_detail
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private lateinit var curHeritage: Heritage

    private fun initView(){

        mainActivityViewModel.setPageTitle("문화재 상세")

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    heritageViewModel.curHeritage.collect{
                        curHeritage = it
                        binding.culturalAssetDetailTvName.text = it.name
                        Glide.with(requireContext())
                            .load(it.imageUrl)
                            .fitCenter()
                            .into(binding.culturalAssetDetailIv)
                        binding.culturalAssetDetailTvDescription.text = it.content
//                   if(it.gameEnable){
//                       mainActivityViewModel.addDetailState(arrayListOf(DetailStateEnum.MiniGame))
//                   }
                        if(mainActivityViewModel.gameEnableList.value.contains(it.id)){
                            binding.culturalAssetMiniGames.visibility = View.VISIBLE
                            binding.culturalAssetDetailBtnAddToTravel.visibility = View.GONE
                        }else if(mainActivityViewModel.detailState.value.contains(DetailStateEnum.AddToTravel)
                            && !travelViewModel.travelPlanHeritageList.value.contains(it)){
                            binding.culturalAssetMiniGames.visibility = View.GONE
                            binding.culturalAssetDetailBtnAddToTravel.visibility = View.VISIBLE
                        }else{
                            binding.culturalAssetMiniGames.visibility = View.GONE
                            binding.culturalAssetDetailBtnAddToTravel.visibility = View.GONE
                        }
                    }
                }
            }
        }

        binding.culturalAssetDetailBtnAddToTravel.setOnClickListener {
            travelViewModel.addHeritageToTravelPlan(heritageViewModel.curHeritage.value)
            findNavController().popBackStack(R.id.travelPlanFragment, false)
        }

        binding.culturalAssetDetailBtnNewRecord.setOnClickListener {
            boardViewModel.setIsHeritageBoard(true)
            findNavController().navigate(R.id.travelPostEditFragment)
        }

        binding.culturalAssetDetailBtnNavigation.setOnClickListener {
            kakaoNavi()
        }

        binding.culturalAssetDetailBtnGame.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }

        binding.culturalAssetDetailBtnGame2.setOnClickListener {
            findNavController().navigate(R.id.miniGame2Fragment)
        }

        binding.culturalAssetDetailBtnReadRecord.setOnClickListener {
            boardViewModel.setSearchBoardTags(listOf(
                "email=${accountViewModel.user.value.memberEmail}", "heritage=${curHeritage.id}"
            ))
            findNavController().navigate(R.id.boardListFragment)
        }
    }


    private fun kakaoNavi(){
        if (NaviClient.instance.isKakaoNaviInstalled(requireContext())) {
            startActivity(
                NaviClient.instance.navigateIntent(
                    Location(curHeritage.name, curHeritage.lng, curHeritage.lat),
                    NaviOption(coordType = CoordType.WGS84),
                )
            )
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.WEB_NAVI_INSTALL)
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

    }
}