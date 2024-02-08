package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.collection.arraySetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.Posting
import com.ssafy.travelcollector.dto.TravelTheme
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainActivityViewModel"

enum class DetailStateEnum(private val state: Int) {
    None(0),
    AddToTravel(1),
    MiniGame(2)
}

class MainActivityViewModel : ViewModel() {

    private val _geofenceList = MutableStateFlow(mutableListOf<Geofence>())
    val geofenceList = _geofenceList.asStateFlow()

    private val _gameEnableList = MutableStateFlow(mutableListOf<Int>())
    val gameEnableList = _gameEnableList.asStateFlow()

    fun addGameEnableHeritage(id: Int){
        val newList = _gameEnableList.value.toMutableList()
        newList.add(id)
        _gameEnableList.update { newList }
    }

    fun removeGameEnableHeritage(id: Int){
        val newList = _gameEnableList.value.toMutableList()
        newList.remove(id)
        _gameEnableList.update { newList }
    }

    fun createGeofenceList(list: ArrayList<Heritage>){
        val newList = mutableListOf<Geofence>()
        for(heritage in list){
            val geofence = Geofence.Builder()
                .setRequestId(heritage.id.toString())
                .setCircularRegion(heritage.lat.toDouble(), heritage.lng.toDouble(), 20f)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setLoiteringDelay(10000)
                .setTransitionTypes(
                    Geofence.GEOFENCE_TRANSITION_ENTER
                            or Geofence.GEOFENCE_TRANSITION_EXIT
                            or Geofence.GEOFENCE_TRANSITION_DWELL
                )
                .build()
            newList.add(geofence)
        }
        _geofenceList.update { newList }
    }



    private val _curLocation = MutableStateFlow(Pair(0.0, 0.0))
    val curLocation = _curLocation.asStateFlow()
    fun setCurLocation(lat: Double, lng: Double){
        _curLocation.update { Pair(lat, lng) }
    }

    private val _detailState = MutableStateFlow(arraySetOf(DetailStateEnum.None))
    val detailState = _detailState.asStateFlow()

    fun addDetailState(states: ArrayList<DetailStateEnum>){
        _detailState.update {
            it.addAll(states)
            it
        }
    }

    fun removeDetailState(state: DetailStateEnum){
        _detailState.update {
            if(it.contains(state)) it.remove(state)
            it
        }
    }

    private val _selectedPostingId = MutableStateFlow(0)
    val selectedPostingId = _selectedPostingId.asStateFlow()
    fun setSelectedPostingId(id: Int){
        _selectedPostingId.value = id
    }


    private val _posting = MutableStateFlow(arrayListOf<Posting>())
    val posting = _posting.asStateFlow()

    private var _recommendedTheme = MutableStateFlow(arrayListOf(
        TravelTheme(title = "1", isBookMarked = false),
        TravelTheme(title = "2", isBookMarked = true),
        TravelTheme(title = "3", isBookMarked = false)
    ))
    val recommendedTheme = _recommendedTheme.asStateFlow()
    fun setRecommendedTheme(list: ArrayList<TravelTheme>){
        _recommendedTheme.update{ ArrayList(list) }
        saveRecommendedTheme()
    }

    private fun saveRecommendedTheme(){
        //rest 통신을 하여 기존의 즐겨찾기 테마 정보를 수정한다
    }

    fun loadRecommendedTheme(){
        //rest 통신을 하여 각 여행의 문화재 리스트를 불러온다
    }

}