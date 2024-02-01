package com.ssafy.travelcollector.fragment.travel

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
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar.getInstance
import java.util.Collections
import java.util.Locale

private const val TAG = "TravelPlanFragment"
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(FragmentTravelPlanBinding::bind,
    R.layout.fragment_travel_plan
) {

    private val heritageAdapter: HeritageAdapter by lazy{
        HeritageAdapter()
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

    private fun initView(){
        var startDate = 0L
        var endDate = 0L

        binding.travelListDaySelect.setOnClickListener{
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("기간 선택").build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
//                val calendar = getInstance()
//                calendar.timeInMillis = selection?.first ?: 0
//                val startDate = SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(calendar.time).toString()
//                calendar.timeInMillis = selection?.second ?: 0
//                val endDate = SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(calendar.time).toString()
//                Log.d(TAG, "initView: $startDate $endDate")
                startDate = selection?.first ?: 0
                endDate = selection?.first ?: 0
                binding.travelPlanTvDuration.text = dateRangePicker.headerText

            }
        }

        binding.travelPlanBtnRecommendTheme.setOnClickListener {
            mainActivityViewModel.addDetailState(DetailStateEnum.AddToTravel)
            findNavController().navigate(R.id.themeListFragment)
        }

        binding.travelPlanFabAdd.setOnClickListener{
            mainActivityViewModel.addDetailState(DetailStateEnum.AddToTravel)
            findNavController().navigate(R.id.heritageListFragment)
        }

        binding.travelPlanBtnSave.setOnClickListener {
            if(startDate==0L||endDate==0L){
                showToast("날짜를 입력하세요")
            }else{
                mainActivityViewModel.addTravel(
                    TravelWithHeritageList(
                        name = binding.travelPlanEtName.text.toString(),
                        startDate = startDate,
                        endDate = endDate,
                        heritageList = mainActivityViewModel.travelPlanHeritageList.value
                    )
                )
            }

        }
    }

    private fun initAdapter(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    mainActivityViewModel.travelPlanHeritageList.collect{
                        heritageAdapter.submitList(it)
                    }
                }
            }
        }


        val itemTouchCallBack = ItemTouchHelper(ItemTouchCallBack())
        itemTouchCallBack.attachToRecyclerView(binding.travelPlanRv)

        heritageAdapter.eventListener = object : HeritageAdapter.EventListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchCallBack.startDrag(viewHolder)
            }

            override fun delete(position: Int) {
                val newList = mainActivityViewModel.travelPlanHeritageList.value
                newList.removeAt(position)
                mainActivityViewModel.setTravelPlanHeritageList(newList)
            }

            override fun onRemove(position: Int) {
                val newList = mainActivityViewModel.travelPlanHeritageList.value.toMutableList()
                newList.removeAt(position)
                mainActivityViewModel.setTravelPlanHeritageList(newList as ArrayList)
            }

            override fun onMove(from: Int, to: Int) {
                val newList = mainActivityViewModel.travelPlanHeritageList.value.toMutableList()
                Collections.swap(newList, from, to)
                mainActivityViewModel.setTravelPlanHeritageList(newList as ArrayList)
            }
        }

        binding.travelPlanRv.adapter = heritageAdapter

    }


}