package com.ssafy.travelcollector

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.adapter.main.MainHeritageAdapter
import com.ssafy.travelcollector.adapter.main.MainPostingAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMainBinding
import com.ssafy.travelcollector.util.TimeConverter
import kotlinx.coroutines.launch
import kotlin.math.min

private const val TAG = "MainFragment"
class MainFragment : BaseFragment<FragmentMainBinding> (FragmentMainBinding::bind, R.layout.fragment_main){

    private val mainHeritageAdapter: MainHeritageAdapter by lazy{
        MainHeritageAdapter()
    }

    private val mainBoardAdapter: MainPostingAdapter by lazy{
        MainPostingAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setNavigationBarStatus(true)

        lifecycleScope.launch{
            accountViewModel.user.collect{
                Log.d(TAG, "onViewCreated: $it")
            }
        }

        initView()
        initAdapter()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        lifecycleScope.launch {
            launch {
                travelViewModel.loadOnGoingTravel()
                travelViewModel.loadUserTravelList()
                launch {
                    travelViewModel.onGoingTravel.collect{ travel ->
                        if(travel.id!=-1){
                            mainActivityViewModel.createGeofenceList(
                                travel.heritageList
                            )
                            binding.mainCurTravelView.visibility = View.VISIBLE
                            binding.mainTvAltText.visibility = View.GONE
                            binding.mainOli.setImages(ArrayList(travel.heritageList.map{it.imageUrl}))
                            binding.mainTravelTitle.text = travel.name
                            binding.mainTvDuration.text =
                                "${TimeConverter.timeMilliToDateString(travel.startDate)} ~ ${TimeConverter.timeMilliToDateString(travel.endDate)}"
                        }else{
                            travelViewModel.userTravelList.collect{lst->
                                if(lst.isNotEmpty()){
                                    binding.mainCurTravelView.visibility = View.VISIBLE
                                    binding.mainTvAltText.visibility = View.GONE
                                    val first = lst[0]
                                    binding.mainOli.setImages(ArrayList(first.heritageList.map{it.imageUrl}))
                                    binding.mainTravelTitle.text = first.name
                                    binding.mainTvDuration.text =
                                        "${TimeConverter.timeMilliToDateString(first.startDate)} ~ ${TimeConverter.timeMilliToDateString(first.endDate)}"
                                }else{
                                    binding.mainCurTravelView.visibility = View.GONE
                                    binding.mainTvAltText.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }


            }

            launch {
                travelViewModel.loadUserTravelList()
                travelViewModel.userTravelList.collect{ travel->
                    if(travel.size>0){
                        binding.mainOli.setImages(ArrayList(travel[0].heritageList.map { it.imageUrl }))
                        binding.mainTravelTitle.text = travel[0].name
                        val startDate = TimeConverter.timeMilliToDateString(travel[0].startDate)
                        val endDate = TimeConverter.timeMilliToDateString(travel[0].endDate)
                        binding.mainTvDuration.text = "$startDate ~ $endDate"
                    }
                }
            }

            launch {
                heritageViewModel.searchHeritageListRandom(null, null, null, null)
            }

            launch {
                boardViewModel.loadAllBoards()
            }

        }

        binding.mainTvBtnBoardShowAll.setOnClickListener {
            findNavController().navigate(R.id.boardPostFragment)
        }
        binding.mainTvBtnBoardShowAll.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_boardListFragment)
        }
    }

    private fun initAdapter(){
        mainBoardAdapter.clickListener = object : MainPostingAdapter.IClickListener{
            override fun onClick(id: Int) {
                boardViewModel.loadDetailBoard(id)
                findNavController().navigate(R.id.boardPostFragment)
            }
        }

        lifecycleScope.launch {
            launch {
                boardViewModel.boardList.collect{
                    mainBoardAdapter.submitList(it.take(min(it.size, 3)))
                }
            }

            launch {
                heritageViewModel.curHeritageList.collect{
                    mainHeritageAdapter.submitList(it)
                }
            }
        }

        binding.mainPostRv.adapter = mainBoardAdapter
        binding.mainCultureHeritageRv.adapter = mainHeritageAdapter
    }


}