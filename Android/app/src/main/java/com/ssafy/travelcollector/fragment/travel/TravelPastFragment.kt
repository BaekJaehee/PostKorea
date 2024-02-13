package com.ssafy.travelcollector.fragment.travel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.HeritageAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPastBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.util.TimeConverter
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import kotlinx.coroutines.launch
import java.util.Collections

class TravelPastFragment : BaseFragment<FragmentTravelPastBinding>(FragmentTravelPastBinding::bind, R.layout.fragment_travel_past) {

    private val heritageAdapter: HeritageAdapter by lazy{ HeritageAdapter(false) }
    private lateinit var curTravel: TravelWithHeritageList


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }


    private fun initView(){
        curTravel = travelViewModel.userTravel.value
        mainActivityViewModel.addDetailState(arrayListOf(DetailStateEnum.WatchingTravel))
        val startDateString = TimeConverter.timeMilliToDateString(curTravel.startDate)
        val endDateString = TimeConverter.timeMilliToDateString(curTravel.endDate)
        binding.travelPastTvDuration.text = "$startDateString ~ $endDateString"
        binding.travelPastTravelName.text = curTravel.name
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            travelViewModel.travelPlanHeritageList.collect{
                heritageAdapter.submitList(it)
            }
        }

        heritageAdapter.eventListener = object : HeritageAdapter.EventListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {}
            override fun delete(position: Int) {}
            override fun onRemove(position: Int) {}
            override fun onMove(from: Int, to: Int) {}
            override fun onClick(position: Int) {
                heritageViewModel.setCurHeritage(travelViewModel.travelPlanHeritageList.value[position])
                findNavController().navigate(R.id.culturalAssetDetailFragment)
            }
        }

        binding.travelPastRv.adapter = heritageAdapter
    }

    override fun onDestroyView() {
        mainActivityViewModel.removeDetailState(DetailStateEnum.WatchingTravel)
        super.onDestroyView()
    }


}