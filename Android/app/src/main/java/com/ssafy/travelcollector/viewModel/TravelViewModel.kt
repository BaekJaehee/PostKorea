package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.TravelPlanResponse
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "TravelViewModel"
class TravelViewModel: ViewModel() {

    private val _userTravelId = MutableStateFlow(-1)
    val userTravelId = _userTravelId.asStateFlow()

    fun setUserTravelId(id: Int){
        _userTravelId.update { id }
    }

    private val _userTravel = MutableStateFlow(TravelWithHeritageList())
    val userTravel=_userTravel.asStateFlow()
    fun setUserTravel(travel: TravelWithHeritageList){
        _userTravel.update { travel }
    }

    //upcoming
    private val _userTravelList = MutableStateFlow(arrayListOf<TravelWithHeritageList>())
    val userTravelList = _userTravelList.asStateFlow()

    //completed
    private val _completedTravelList = MutableStateFlow(arrayListOf<TravelWithHeritageList>())
    val completedTravelList = _completedTravelList.asStateFlow()

    private val _isPlanning = MutableStateFlow(true)
    val isPlanning = _isPlanning.asStateFlow()

    fun setWatchingState(isPlanning: Boolean){
        _isPlanning.update { isPlanning }
    }

    fun addTravel(newTravel: TravelWithHeritageList){
        val heritageIdList = newTravel.heritageList.map { it.id }

        viewModelScope.launch{
            RetrofitUtil.TRAVEL_SERVICE.addHeritageListToTravelPlan(
                token = AccountViewModel.ACCESS_TOKEN,
                travelId = withContext(Dispatchers.IO){
                    RetrofitUtil.TRAVEL_SERVICE.planTravel(
                        AccountViewModel.ACCESS_TOKEN, newTravel
                    ).body()!!.planId
                },
                travelList = heritageIdList
            )
        }
        loadUserTravelList()
        loadOnGoingTravel()
    }

    fun updateTravel(newTravel: TravelWithHeritageList){
        val heritageIdList = newTravel.heritageList.map { it.id }
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                RetrofitUtil.TRAVEL_SERVICE.deleteHeritageListToTravelPlan(
                    token = AccountViewModel.ACCESS_TOKEN,
                    travelId = userTravelId.value,
                    travelList = listOf()
                ).code()
            }
            if(response/100 == 2){
                RetrofitUtil.TRAVEL_SERVICE.addHeritageListToTravelPlan(
                    token = AccountViewModel.ACCESS_TOKEN,
                    travelId = userTravelId.value,
                    travelList = heritageIdList
                )
            }
            loadUserTravelList()
        }
    }

    private fun setHeritageListOfTravel(responseList: List<TravelPlanResponse>, isPlanning: Boolean){
        viewModelScope.launch {
            val newTravel = withContext(Dispatchers.IO){
                val newTravel = arrayListOf<TravelWithHeritageList>()
                for(res in responseList){
                    newTravel.add(TravelWithHeritageList(
                        id = res.planId,
                        name = res.name,
                        startDate = res.startDate,
                        endDate = res.endDate,
                        condition = res.condition,
                        heritageList = ArrayList(
                            withContext(Dispatchers.IO){
                                RetrofitUtil.TRAVEL_SERVICE.getHeritageListOfTravel(
                                    AccountViewModel.ACCESS_TOKEN, res.planId
                                ).body()!!
                            })
                    ))
                }
                newTravel
            }
            if(isPlanning) _userTravelList.update { ArrayList(newTravel.sortedBy { it.startDate }) }
            else _completedTravelList.update { ArrayList(newTravel.sortedByDescending { it.startDate }) }
        }

    }

    fun loadUserTravelList(){
        viewModelScope.launch {
            val upcoming = withContext(Dispatchers.IO){ RetrofitUtil.TRAVEL_SERVICE.getUpcomingTravelList(AccountViewModel.ACCESS_TOKEN) }
            val completed = withContext(Dispatchers.IO){RetrofitUtil.TRAVEL_SERVICE.getCompletedTravelList(AccountViewModel.ACCESS_TOKEN)}
            if(upcoming.code()/100 == 2) setHeritageListOfTravel(upcoming.body()!!, true)
            if(completed.code()/100 == 2) setHeritageListOfTravel(completed.body()!!, false)
        }
    }

    private val _onGoingTravel = MutableStateFlow(TravelWithHeritageList())
    val onGoingTravel = _onGoingTravel.asStateFlow()

    fun loadOnGoingTravel(){
        viewModelScope.launch {
            val ongoing = withContext(Dispatchers.IO){RetrofitUtil.TRAVEL_SERVICE.getOngoingTravelList(AccountViewModel.ACCESS_TOKEN)}
            if(ongoing.code()/100 == 2 && ongoing.body()!!.isNotEmpty()){
                val res = ongoing.body()!![0]
                val travel = TravelWithHeritageList(
                    id = res.planId,
                    name = res.name,
                    startDate = res.startDate,
                    endDate = res.endDate,
                    condition = res.condition,
                    heritageList = ArrayList(
                        withContext(Dispatchers.IO){
                            RetrofitUtil.TRAVEL_SERVICE.getHeritageListOfTravel(
                                AccountViewModel.ACCESS_TOKEN, res.planId
                            ).body()!!
                        })
                )
                _onGoingTravel.update { travel }
            }
        }
    }

    fun deleteTravel(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.TRAVEL_SERVICE.deleteTravel(
                    AccountViewModel.ACCESS_TOKEN, userTravelId.value
                )
            }
            if(res.code()/100==2){
                loadOnGoingTravel()
                loadUserTravelList()
            }else{
                Log.d(TAG, "deleteTravel: ${res}")
            }
        }
    }

    //계획 중인 여행 목록의 원본. 저장 시 해당 리스트로 저장.
    private val _travelPlanHeritageList = MutableStateFlow(arrayListOf<Heritage>())
    val travelPlanHeritageList = _travelPlanHeritageList.asStateFlow()

    fun setTravelPlanHeritageList(list: List<Heritage>){
        _travelPlanHeritageList.update { ArrayList(list) }
    }

    fun addHeritageToTravelPlan(heritage: Heritage){
        val newList = _travelPlanHeritageList.value.toMutableSet()
        newList.add(heritage)
        _travelPlanHeritageList.update { ArrayList(newList) }
    }

    fun updateMiniGameEnable(list:List<Int>){
        val newList = _travelPlanHeritageList.value.toMutableList()
        for((idx, it) in newList.withIndex()){
            if(list.contains(it.id)){
                newList[idx] = it.copy(gameEnable = true)
            }
        }
        setTravelPlanHeritageList(newList)
    }

}