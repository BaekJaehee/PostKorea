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
        mainActivityViewModel.setPageTitle("")

        lifecycleScope.launch {

            launch {
                travelViewModel.loadOnGoingTravel()
                travelViewModel.loadUserTravelList()
            }

            launch {
                travelViewModel.onGoingTravel.collect{ travel ->
                    if(travel.id!=-1){
                        mainActivityViewModel.createGeofenceList(
                            travel.heritageList
                        )
                        setMyTravel(travel)
                    }
                }
            }

            launch {
                travelViewModel.userTravelList.collect{lst->
                    if(travelViewModel.onGoingTravel.value.id == -1 && lst.isNotEmpty()){
                        val first = lst[0]
                        setMyTravel(first)
                    }
                }
            }

            launch{
                travelViewModel.completedTravelList.collect{ lst->
                    if(travelViewModel.onGoingTravel.value.id == -1
                        && travelViewModel.userTravelList.value.isEmpty()){
                        if(lst.isNotEmpty()){
                            val travel = lst[Random.nextInt(lst.size)]
                            setMyTravel(travel)
                        }else{
                            binding.mainCurTravelView.visibility = View.GONE
                            binding.mainTvAltText.visibility = View.VISIBLE
                        }
                    }
                }

            }



            launch {
                heritageViewModel.searchHeritageListRandom(null, null, null, null)
            }

            launch {
                boardViewModel.setSearchBoardTags(listOf())
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

    @SuppressLint("SetTextI18n")
    private fun setMyTravel(travel: TravelWithHeritageList){
        binding.mainCurTravelView.visibility = View.VISIBLE
        binding.mainTvAltText.visibility = View.GONE
        binding.mainOli.setImages(ArrayList(travel.heritageList.map{it.imageUrl}))
        binding.mainTravelTitle.text = travel.name
        binding.mainTvDuration.text =
            "${TimeConverter.timeMilliToDateString(travel.startDate)} ~ ${TimeConverter.timeMilliToDateString(travel.endDate)}"
    }

}