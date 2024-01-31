package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.lifecycle.ViewModel
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.Posting
import com.ssafy.travelcollector.dto.TravelTheme
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.test.TDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "MainActivityViewModel"

enum class DetailStateEnum(private val state: Int) {
    None(0),
    AddToTravel(1),
    MiniGame(2),
}

class MainActivityViewModel : ViewModel() {

    val detailState = arraySetOf(DetailStateEnum.None)

    fun addDetailState(state: DetailStateEnum): MainActivityViewModel{
        detailState.add(state)
        return this
    }

    private val _selectedPostingId = MutableStateFlow(0)
    val selectedPostingId = _selectedPostingId.asStateFlow()
    fun setSelectedPostingId(idx: Int){
        _selectedPostingId.value = idx
    }

    private val _posting = MutableStateFlow(arrayListOf<Posting>())
    val posting = _posting.asStateFlow()


    private val _userInfoToSignUp = MutableStateFlow(User())
    private val userInfoToSignUP = _userInfoToSignUp.asStateFlow()
    fun passUserInfoToSignUp(user:User){
        _userInfoToSignUp.update{user}

    }
    fun getUserInfoToSignUp(): User{
        return  userInfoToSignUP.value
    }

    private var _travelPlanHeritageList = MutableStateFlow(arrayListOf(Heritage(name="3"), Heritage(name="4")))
    val travelPlanHeritageList = _travelPlanHeritageList.asStateFlow()
    fun loadTravelPlanHeritageList(){
        //rest 통신을 하여 각 여행의 문화재 리스트를 불러온다
    }

    fun setTravelPlanHeritageList(list: ArrayList<Heritage>){
        _travelPlanHeritageList.update { list }
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

    private var _curHeritageList = MutableStateFlow(arrayListOf(Heritage(name = "1"), Heritage(name = "2")))
    val curHeritageList = _curHeritageList.asStateFlow()
    fun setCurHeritageList(list: ArrayList<Heritage>){
        _curHeritageList.update { list }
    }

}