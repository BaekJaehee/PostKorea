package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.collection.arraySetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    MiniGame(2),
}

class MainActivityViewModel : ViewModel() {

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

    private val _selectedPostingId = MutableStateFlow(0)
    val selectedPostingId = _selectedPostingId.asStateFlow()
    fun setSelectedPostingId(idx: Int){
        _selectedPostingId.value = idx
    }

    private val _posting = MutableStateFlow(arrayListOf<Posting>())
    val posting = _posting.asStateFlow()

    private val _curHeritage = MutableStateFlow(Heritage())
    val curHeritage = _curHeritage.asStateFlow()
    fun setCurHeritage(heritage: Heritage){
        _curHeritage.update { heritage }
    }


    //눈에 보이는 여행 목록. db에 저장되지 않는 임시 리스트.
    //테마 내의 목록이나 문화재 검색 결과가 될 수 있다
    private var _curHeritageList = MutableStateFlow(arrayListOf(Heritage(name = "1"), Heritage(name = "2")))
    val curHeritageList = _curHeritageList.asStateFlow()
    fun setCurHeritageList(list: ArrayList<Heritage>){
        _curHeritageList.update { list }
    }

    fun loadHeritageList(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.getHeritageList()
            }
            setCurHeritageList(ArrayList(result.body()))
        }
    }

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