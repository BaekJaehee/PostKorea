package com.ssafy.travelcollector.fragment.theme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.HeritageAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentThemeDetailBinding
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import kotlinx.coroutines.launch

private const val TAG = "ThemeDetailFragment"
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
        binding.themeDetailTitle.text = mainActivityViewModel.curTheme.value.title
        binding.themeDetailDescription.text = mainActivityViewModel.curTheme.value.description
        binding.themeDetailAdd.visibility = if(mainActivityViewModel.detailState.value.contains(DetailStateEnum.AddToTravel)) View.VISIBLE else View.GONE
        binding.themeDetailAdd.setOnClickListener{
            val newList = travelViewModel.travelPlanHeritageList.value.toMutableSet()
            newList.addAll(mainActivityViewModel.curTheme.value.heritageDetailList)
            travelViewModel.setTravelPlanHeritageList(ArrayList(newList))
            findNavController().popBackStack(R.id.travelPlanFragment, false)
        }
    }

    private fun initAdapter(){

        heritageAdapter.eventListener = object : HeritageAdapter.EventListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {}

            override fun delete(position: Int) {}

            override fun onRemove(position: Int) {}

            override fun onMove(from: Int, to: Int) {}

            override fun onClick(position: Int) {
                heritageViewModel.setCurHeritage(mainActivityViewModel.curTheme.value.heritageDetailList[position])
                findNavController().navigate(R.id.culturalAssetDetailFragment)
            }
        }

        lifecycleScope.launch {
            mainActivityViewModel.curTheme.collect{
                heritageAdapter.submitList(it.heritageDetailList)
            }
        }

        binding.themeDetailHeritageRv.adapter = heritageAdapter

    }

}