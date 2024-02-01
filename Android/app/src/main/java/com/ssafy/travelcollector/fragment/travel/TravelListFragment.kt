package com.ssafy.travelcollector.fragment.travel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.TravelAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelListBinding
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
            Navigation.findNavController(view).navigate(R.id.travelPlanFragment)
        }
        initAdapter()
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            mainActivityViewModel.userTravelList.collect{
                travelAdapter.submitList(it)
            }
        }
        binding.travelListRvMyTravel.adapter = travelAdapter
    }

}