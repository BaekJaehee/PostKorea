package com.ssafy.travelcollector.viewModel

import androidx.lifecycle.ViewModel
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
        // db에 값 넣어주기
        _userTravelList.update { it ->
            it.apply{
                add(newTravel)
                sortBy { it.startDate }
            }
        }
    }

    fun setUserTravelList(newList: ArrayList<TravelWithHeritageList>){
        _userTravelList.update { newList }
    }

    //계획 중인 여행 목록의 원본. 저장 시 해당 리스트로 저장.
    private val _travelPlanHeritageList = MutableStateFlow(arrayListOf(Heritage(name="3"), Heritage(name="4")))
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