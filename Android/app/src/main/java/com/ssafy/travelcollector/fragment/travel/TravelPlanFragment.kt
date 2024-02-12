package com.ssafy.travelcollector.fragment.travel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPlanBinding
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.HeritageAdapter
import com.ssafy.travelcollector.config.ItemTouchCallBack
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.util.RetrofitUtil
import com.ssafy.travelcollector.util.TimeConverter
import com.ssafy.travelcollector.viewModel.AccountViewModel
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

private const val TAG = "TravelPlanFragment"
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(FragmentTravelPlanBinding::bind,
    R.layout.fragment_travel_plan
) {

    private val heritageAdapter: HeritageAdapter by lazy{
        HeritageAdapter(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DB 불러오기
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private lateinit var curTravel: TravelWithHeritageList

    @SuppressLint("SetTextI18n")
    private fun initView(){

        var startDate = 0L
        var endDate = 0L

        curTravel = travelViewModel.userTravel.value

        if(curTravel.id == -1){
//            if(!mainActivityViewModel.detailState.value.contains(DetailStateEnum.AddToTravel))
            mainActivityViewModel.setPageTitle("탐방 계획 작성")
            binding.travelPlanTvDuration.text = "기간을 선택하세요"
        }else{
            mainActivityViewModel.setPageTitle("탐방 계획 상세")
            val startDateString = TimeConverter.timeMilliToDateString(curTravel.startDate)
            val endDateString = TimeConverter.timeMilliToDateString(curTravel.endDate)
            binding.travelPlanTvDuration.text = "$startDateString ~ $endDateString"
            binding.travelPlanEtName.setText(curTravel.name)
            startDate = curTravel.startDate
            endDate = curTravel.endDate
        }

        binding.travelListDaySelect.setOnClickListener{
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("기간 선택").build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                startDate = selection?.first ?: 0
                endDate = selection?.second ?: 0
                binding.travelPlanTvDuration.text = dateRangePicker.headerText
            }
        }

        binding.travelPlanBtnRecommendTheme.setOnClickListener {
            mainActivityViewModel.addDetailState(arrayListOf(DetailStateEnum.AddToTravel))
            findNavController().navigate(R.id.themeListFragment)
        }

        binding.travelPlanFabAdd.setOnClickListener{
            mainActivityViewModel.addDetailState(arrayListOf(DetailStateEnum.AddToTravel))
            findNavController().navigate(R.id.action_travelPlanFragment_to_heritageListFragment)
        }

        binding.travelPlanBtnSave.setOnClickListener {
            if(startDate==0L||endDate==0L){
                showToast("날짜를 입력하세요")
            }else{
                val newTravel = TravelWithHeritageList(
                    name = binding.travelPlanEtName.text.toString(),
                    startDate = startDate,
                    endDate = endDate,
                    heritageList = ArrayList(heritageAdapter.currentList)
                )

                if(curTravel.id == -1){
                    travelViewModel.addTravel(newTravel)
                }else{
                    travelViewModel.updateTravel(newTravel)
                }
                findNavController().popBackStack()
            }

        }
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            launch {
                mainActivityViewModel.gameEnableList.collect{
                    travelViewModel.updateMiniGameEnable(ArrayList(it))
                }
            }

            launch {
                travelViewModel.travelPlanHeritageList.collect{
                    Log.d(TAG, "initAdapter: $it")
                    heritageAdapter.submitList(it)
                }
            }

//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                launch {
//                    travelViewModel.travelPlanHeritageList.collect{
//                        heritageAdapter.submitList(it)
//                    }
//                }
//            }

        }

        val itemTouchCallBack = ItemTouchHelper(ItemTouchCallBack())
        itemTouchCallBack.attachToRecyclerView(binding.travelPlanRv)

        heritageAdapter.eventListener = object : HeritageAdapter.EventListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchCallBack.startDrag(viewHolder)
            }

            override fun delete(position: Int) {
                val newList = travelViewModel.travelPlanHeritageList.value.toMutableList()
                newList.removeAt(position)
                travelViewModel.setTravelPlanHeritageList(ArrayList(newList))
            }

            override fun onRemove(position: Int) {
                val newList = travelViewModel.travelPlanHeritageList.value.toMutableList()
                newList.removeAt(position)
                travelViewModel.setTravelPlanHeritageList(newList as ArrayList)
            }

            override fun onMove(from: Int, to: Int) {
                val newList = travelViewModel.travelPlanHeritageList.value.toMutableList()
                Collections.swap(newList, from, to)
                travelViewModel.setTravelPlanHeritageList(newList as ArrayList)
            }

            override fun onClick(position: Int) {
                heritageViewModel.setCurHeritage(travelViewModel.travelPlanHeritageList.value[position])
                findNavController().navigate(R.id.culturalAssetDetailFragment)
            }
        }

        binding.travelPlanRv.adapter = heritageAdapter

    }


}