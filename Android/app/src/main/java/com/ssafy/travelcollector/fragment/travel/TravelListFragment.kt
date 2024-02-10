package com.ssafy.travelcollector.fragment.travel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.TravelAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelListBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import kotlinx.coroutines.launch

class TravelListFragment : BaseFragment<FragmentTravelListBinding> (FragmentTravelListBinding::bind,
    R.layout.fragment_travel_list
){

    private val travelAdapter : TravelAdapter by lazy{
        TravelAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.travelListAddBtnMyTravel.setOnClickListener{
            travelViewModel.apply {
                setTravelPlanHeritageList(arrayListOf())
                setUserTravel(TravelWithHeritageList())
            }
            findNavController().navigate(R.id.travelPlanFragment)
        }
        initView()
        initAdapter()
    }

    private fun initView(){
        lifecycleScope.launch {
            travelViewModel.loadUserTravelList()
            travelViewModel.loadOnGoingTravel()
            travelViewModel.onGoingTravel.collect{
                travel->
                if(travel.id!=-1){
                    binding.travelListOliOngoing.setImages(
                        ArrayList(travel.heritageList.map{it.imageUrl})
                    )
                }else{
                    binding.travelListViewOngoing.visibility = View.GONE
                    binding.travelListTvAltText.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            travelViewModel.userTravelList.collect{
                travelAdapter.submitList(it)
            }
        }
        travelAdapter.clickListener = object : TravelAdapter.ClickListener{
            override fun onClick(position: Int, state: Boolean) {
                val curTravel = travelViewModel.userTravelList.value[position]
                travelViewModel.apply {
                    setTravelPlanHeritageList(curTravel.heritageList)
                    setUserTravel(curTravel)
                    setUserTravelId(curTravel.id)
                }
                if(state){
                    findNavController().navigate(R.id.travelPlanFragment)
                }
            }
        }

        binding.travelListRvMyTravel.adapter = travelAdapter
    }

}