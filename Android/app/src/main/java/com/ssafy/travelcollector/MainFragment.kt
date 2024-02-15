package com.ssafy.travelcollector

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.adapter.main.MainHeritageAdapter
import com.ssafy.travelcollector.adapter.main.MainPostingAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMainBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.util.TimeConverter
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.random.Random

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

        initView()
        initAdapter()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        mainActivityViewModel.setPageTitle("POST KOREA")

        lifecycleScope.launch {
            launch {
                mainActivityViewModel.loadVisitedHeritage()
            }

            launch {
                travelViewModel.loadOnGoingTravel()
                travelViewModel.loadUserTravelList()
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {
                        travelViewModel.onGoingTravel.collect{ travel ->
                            if(travel.id!=-1){
                                mainActivityViewModel.createGeofenceList(
                                    travel.heritageList
                                )
                                binding.mainTravelTitle.setTextColor(binding.root.resources.getColor(R.color.orange))
                                binding.mainTvDuration.setTextColor(binding.root.resources.getColor(R.color.orange))
                                setMyTravel(travel)
                                binding.mainCurTravelView.setOnClickListener {
                                    travelViewModel.apply {
                                        val curTravel = travelViewModel.onGoingTravel.value
                                        setTravelPlanHeritageList(curTravel.heritageList)
                                        setUserTravel(curTravel)
                                        setUserTravelId(curTravel.id)
                                        findNavController().navigate(R.id.travelPlanFragment)
                                    }
                                }
                            }
                        }
                    }
                }

            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    travelViewModel.userTravelList.collect{lst->
                        if(travelViewModel.onGoingTravel.value.id == -1 && lst.isNotEmpty()){
                            val first = lst[0]
                            binding.mainTravelTitle.setTextColor(binding.root.resources.getColor(R.color.black))
                            binding.mainTvDuration.setTextColor(binding.root.resources.getColor(R.color.black))
                            setMyTravel(first)
                            binding.mainCurTravelView.setOnClickListener{
                                travelViewModel.apply {
                                    setTravelPlanHeritageList(first.heritageList)
                                    setUserTravel(first)
                                    setUserTravelId(first.id)
                                    findNavController().navigate(R.id.travelPlanFragment)
                                }
                            }
                        }
                    }
                }
            }

            launch{
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    travelViewModel.completedTravelList.collect{ lst->
                        if(travelViewModel.onGoingTravel.value.id == -1
                            && travelViewModel.userTravelList.value.isEmpty()){
                            if(lst.isNotEmpty()){
                                val travel = lst[Random.nextInt(lst.size)]
                                binding.mainTravelTitle.setTextColor(binding.root.resources.getColor(R.color.black))
                                binding.mainTvDuration.setTextColor(binding.root.resources.getColor(R.color.black))
                                setMyTravel(travel)
                                binding.mainCurTravelView.setOnClickListener{
                                    travelViewModel.apply {
                                        setTravelPlanHeritageList(travel.heritageList)
                                        setUserTravel(travel)
                                        setUserTravelId(travel.id)
                                        findNavController().navigate(R.id.travelPastFragment)
                                    }
                                }
                            }else{
                                binding.mainCurTravelView.visibility = View.GONE
                                binding.mainTvAltText.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {
                        heritageViewModel.searchHeritageListRandom(null, null, null, null)
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {
                        boardViewModel.loadAllBoards()
                    }
                }
            }

        }

        binding.mainTvBtnBoardShowAll.setOnClickListener {
            findNavController().navigate(R.id.boardPostFragment)
        }
        binding.mainTvBtnBoardShowAll.setOnClickListener {
            boardViewModel.setSearchBoardTags(arrayListOf())
            findNavController().navigate(R.id.action_mainFragment_to_boardListFragment)
        }
    }

    private fun initAdapter(){
        mainBoardAdapter.clickListener = object : MainPostingAdapter.IClickListener{
            override fun onClick(id: Int) {
                boardViewModel.setCurPostingId(id)
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
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    heritageViewModel.curHeritageList.collect{
                        mainHeritageAdapter.submitList(it)
                    }
                }
            }
        }

        binding.mainPostRv.adapter = mainBoardAdapter
        binding.mainCultureHeritageRv.adapter = mainHeritageAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setMyTravel(travel: TravelWithHeritageList){
        Log.d(TAG, "setMyTravel: $travel")
        binding.mainCurTravelView.visibility = View.VISIBLE
        binding.mainTvAltText.visibility = View.GONE
        binding.mainOli.setImages(ArrayList(travel.heritageList.map{it.imageUrl}))
        binding.mainTravelTitle.text = travel.name
        binding.mainTvDuration.text =
            "${TimeConverter.timeMilliToDateString(travel.startDate)} ~ ${TimeConverter.timeMilliToDateString(travel.endDate)}"
    }

}