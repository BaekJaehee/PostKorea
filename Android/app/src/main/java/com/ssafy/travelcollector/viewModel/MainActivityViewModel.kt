package com.ssafy.travelcollector.viewModel

import androidx.collection.arraySetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.TravelTheme
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
    MiniGame(2),
    WatchingTravel(3)
}

class MainActivityViewModel : ViewModel() {

    private val _geofenceList = MutableStateFlow(mutableListOf<Geofence>())
    val geofenceList = _geofenceList.asStateFlow()

    private val _gameEnableList = MutableStateFlow(mutableSetOf<Int>())
    val gameEnableList = _gameEnableList.asStateFlow()

    fun addGameEnableHeritage(id: Int){
        val newList = _gameEnableList.value.toMutableList()
        newList.add(id)
        _gameEnableList.update { newList.toMutableSet() }
    }

    fun removeGameEnableHeritage(id: Int){
        val newList = _gameEnableList.value.toMutableList()
        newList.remove(id)
        _gameEnableList.update { newList.toMutableSet() }
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

    private val _detailState = MutableStateFlow(mutableSetOf(DetailStateEnum.None))
    val detailState = _detailState.asStateFlow()

    fun addDetailState(states: ArrayList<DetailStateEnum>){
        val newSet = _detailState.value.toMutableSet()
        newSet.addAll(states)
        _detailState.update { newSet }
    }

    fun removeDetailState(state: DetailStateEnum){
        val newSet = _detailState.value.toMutableSet()
        if(newSet.contains(state)) newSet.remove(state)
        _detailState.update { newSet }
    }

    private val _recommendedTheme = MutableStateFlow(arrayListOf<TravelTheme>())
    val recommendedTheme = _recommendedTheme.asStateFlow()

    private val _curTheme = MutableStateFlow(TravelTheme())
    val curTheme = _curTheme.asStateFlow()

    fun setRecommendedTheme(list: ArrayList<TravelTheme>){
        _recommendedTheme.update{ ArrayList(list) }
        saveRecommendedTheme()
    }

    fun setCurTheme(theme: TravelTheme){
        _curTheme.update { theme }
    }

    private fun saveRecommendedTheme(){
        //rest 통신을 하여 기존의 즐겨찾기 테마 정보를 수정한다
    }

    fun loadRecommendedTheme(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.THEME_SERVICE.getThemes()
            }
            if(res.code()/2 == 100){
                val newThemes = res.body()!!
                for(theme in newThemes){
                    theme.heritageDetailList = withContext(Dispatchers.IO){
                        RetrofitUtil.THEME_SERVICE.getThemeDetail(theme.id)
                    }.body()!!.heritageDetailList
                }
                setRecommendedTheme(ArrayList(newThemes))
            }
        }
    }

    fun addVisitedHeritage(id: Int, callback: ()->Unit){
        viewModelScope.launch {
            val res = RetrofitUtil.VISIT_SERVICE.addVisitedHeritage(
                AccountViewModel.ACCESS_TOKEN, id
            )
            if(res.code()/100 == 2){
                callback()
            }
        }
    }

    private val _pageTitle = MutableStateFlow("")
    val pageTitle = _pageTitle.asStateFlow()

    fun setPageTitle(title: String){
        _pageTitle.update { title }
    }
}