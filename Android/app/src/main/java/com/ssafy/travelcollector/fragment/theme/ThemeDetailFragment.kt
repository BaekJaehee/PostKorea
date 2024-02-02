package com.ssafy.travelcollector.fragment.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.HeritageAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentThemeDetailBinding
import com.ssafy.travelcollector.viewModel.DetailStateEnum

class ThemeDetailFragment : BaseFragment<FragmentThemeDetailBinding>(FragmentThemeDetailBinding::bind, R.layout.fragment_theme_detail) {

    private val heritageAdapter: HeritageAdapter by lazy{
        HeritageAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initView(){
        binding.themeDetailAdd.visibility = if(mainActivityViewModel.detailState.value.contains(DetailStateEnum.AddToTravel)) View.VISIBLE else View.GONE
        binding.themeDetailAdd.setOnClickListener{
            val newList = mainActivityViewModel.travelPlanHeritageList.value.toMutableList()
            newList.addAll(mainActivityViewModel.curHeritageList.value)
            mainActivityViewModel.setTravelPlanHeritageList(newList as ArrayList)
            findNavController().popBackStack(R.id.travelPlanFragment, false)
        }
    }

    private fun initAdapter(){
        binding.themeDetailHeritageRv.adapter = heritageAdapter.apply{
            submitList(mainActivityViewModel.curHeritageList.value)
        }

    }

}