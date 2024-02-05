package com.ssafy.travelcollector.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _userTravelList = MutableStateFlow(arrayListOf<TravelWithHeritageList>())
    val userTravelList = _userTravelList.asStateFlow()

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

        _userTravelList.update { it ->
            it.apply{
                add(newTravel)
                sortBy { it.startDate }
            }
        }
    }

    fun loadUserTravelList(){
        viewModelScope.launch {
            val upcoming = withContext(Dispatchers.IO){ RetrofitUtil.TRAVEL_SERVICE.getUpcomingTravelList(AccountViewModel.ACCESS_TOKEN) }
            val ongoing = withContext(Dispatchers.IO){RetrofitUtil.TRAVEL_SERVICE.getOngoingTravelList(AccountViewModel.ACCESS_TOKEN)}
            val completed = withContext(Dispatchers.IO){RetrofitUtil.TRAVEL_SERVICE.getCompletedTravelList(AccountViewModel.ACCESS_TOKEN)}
            val responseList = arrayListOf<TravelPlanResponse>()
            responseList.apply{
                addAll(ArrayList(upcoming.body()!!))
                addAll(ArrayList(ongoing.body()!!))
                addAll(ArrayList(completed.body()!!))
            }

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
            _userTravelList.update {
                ArrayList(newTravel.sortedBy { it.startDate })
            }

        }
    }

    fun setUserTravelList(newList: ArrayList<TravelWithHeritageList>){
        _userTravelList.update { newList }
    }

    //계획 중인 여행 목록의 원본. 저장 시 해당 리스트로 저장.
    private val _travelPlanHeritageList = MutableStateFlow(arrayListOf<Heritage>())
    val travelPlanHeritageList = _travelPlanHeritageList.asStateFlow()
    fun loadTravelPlanHeritageList(){
        //rest 통신을 하여 각 여행의 문화재 리스트를 불러온다
    }

    fun setTravelPlanHeritageList(list: ArrayList<Heritage>){
        _travelPlanHeritageList.update { list }
    }

    fun addHeritageToTravelPlan(heritage: Heritage){
        _travelPlanHeritageList.update {
            it.add(heritage)
            it
        }
    }
}