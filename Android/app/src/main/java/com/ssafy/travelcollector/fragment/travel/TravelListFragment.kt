package com.ssafy.travelcollector.fragment.travel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.TravelAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelListBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.util.TimeConverter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

class TravelListFragment : BaseFragment<FragmentTravelListBinding> (FragmentTravelListBinding::bind,
    R.layout.fragment_travel_list
){

    private val travelAdapter : TravelAdapter by lazy{
        TravelAdapter()
    }

    private var isPlanning = true

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

    @SuppressLint("SetTextI18n")
    private fun initView(){
        mainActivityViewModel.setPageTitle("탐방 리스트")

        binding.btnTravelUpcoming.setOnClickListener {
            isPlanning = true
            binding.btnTravelUpcoming.background.setTint(resources.getColor(R.color.brown2))
            binding.btnTravelCompleted.background.setTint(resources.getColor(R.color.brown3))
            travelViewModel.setWatchingState(true)
        }

        binding.btnTravelCompleted.setOnClickListener {
            isPlanning = false
            binding.btnTravelUpcoming.background.setTint(resources.getColor(R.color.brown3))
            binding.btnTravelCompleted.background.setTint(resources.getColor(R.color.brown2))
            travelViewModel.setWatchingState(false)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    travelViewModel.loadUserTravelList()
                    travelViewModel.loadOnGoingTravel()
                }
                launch {
                    travelViewModel.onGoingTravel.collect{
                            travel->
                        if(travel.id!=-1){
                            binding.travelListOliOngoing.setImages(
                                ArrayList(travel.heritageList.map{it.imageUrl})
                            )
                            binding.travelListTvOngoingTitle.text = travel.name
                            binding.travelListTvDuration.text =
                                "${TimeConverter.timeMilliToDateString(travel.startDate)} ~ ${TimeConverter.timeMilliToDateString(travel.endDate)}"
                        }else{
                            binding.travelListViewOngoing.visibility = View.GONE
                            binding.travelListTvAltText.visibility = View.VISIBLE
                        }
                    }
                }
            }

        }
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            travelViewModel.isPlanning.collect{ state->
                launch {
                    travelViewModel.userTravelList.takeWhile { state }.collect{
                        travelAdapter.submitList(it)
                    }
                    travelViewModel.completedTravelList.takeWhile { !state }.collect{
                        travelAdapter.submitList(it)
                    }
                }
            }
        }

        binding.travelListViewOngoing.setOnClickListener {
            travelViewModel.apply {
                val curTravel = travelViewModel.onGoingTravel.value
                setTravelPlanHeritageList(curTravel.heritageList)
                setUserTravel(curTravel)
                setUserTravelId(curTravel.id)
                findNavController().navigate(R.id.travelPlanFragment)
            }
        }

        travelAdapter.clickListener = object : TravelAdapter.ClickListener{
            override fun onClick(position: Int, state: Boolean) {
                val curTravel: TravelWithHeritageList
                val destination: Int
                if(isPlanning){
                    curTravel = travelViewModel.userTravelList.value[position]
                    destination = R.id.travelPlanFragment
                }else{
                    curTravel = travelViewModel.completedTravelList.value[position]
                    destination = R.id.travelPastFragment
                }
                travelViewModel.apply {
                    setTravelPlanHeritageList(curTravel.heritageList)
                    setUserTravel(curTravel)
                    setUserTravelId(curTravel.id)
                }
                findNavController().navigate(destination)
            }
        }

        binding.travelListRvMyTravel.adapter = travelAdapter
    }

}